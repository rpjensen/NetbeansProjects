/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class Message {
    private final String hostFrom;
    private final String ipFrom;
    private final String hostTo;
    private final String ipTo;
    private final Date timestamp;
    private final String messageBody;
    private final RsaKey rsaKey;
    private final String sessionKey;
    
    private static final DateFormat dateFormat = DateFormat.getDateInstance();
    
    public static class Builder {
        private final String hostFrom;
        private final String ipFrom;
        private String hostTo;
        private String ipTo;
        private Date date;
        private String messageBody;
        private Date timestamp;
        private RsaKey rsaKey;
        private String sessionKey;
        
        public Builder createMessageFrom(Host host){
            return new Builder(host.getHostname(), host.getIP());
        }
        
        private Builder(String hostname, String hostIP){
            this.hostFrom = hostname;
            this.ipFrom = hostIP;
        }
        
        public void setHostTo(String hostName){
            this.hostTo = hostName;
        }
        
        public void setHostIp(String hostIp){
            this.ipTo = hostIp;
        }
        
        private void setDate(Date date){
            this.date = date;
        }
        
        public void setMessageBody(String messageBody){
            this.messageBody = messageBody;
        }
        
        public void setRsaKey(RsaKey rsaKey){
            this.rsaKey = rsaKey;
        }
        
        public void setSessionKey(String sessionKey){
            this.sessionKey = sessionKey;
        }
        
        public Message build(){
            return new Message(hostFrom, ipFrom, hostTo, ipTo, date, messageBody, rsaKey, sessionKey);
        }
    }
    
    private Message(String hostFrom, String ipFrom, String hostTo, String ipTo, Date date, String messageBody, RsaKey rsaKey, String sessionKey){
        this.hostFrom = hostFrom;
        this.ipFrom = ipFrom;
        this.hostTo = hostTo;
        this.ipTo = ipTo;
        if (date == null){
            this.timestamp = new Date();
        }
        else {
            this.timestamp = date;
        }
        this.messageBody = messageBody;
        this.rsaKey = rsaKey;
        this.sessionKey = sessionKey;
    }    
    
    public String getHostFrom(){
        return this.hostFrom;
    }
    
    public String getIpFrom(){
        return this.ipFrom;
    }
    
    public String getHostTo(){
        return this.hostTo;
    }
    
    public String getIpTo(){
        return this.ipTo;
    }
    
    public RsaKey getRsaKey(){
        return this.rsaKey;
    }
    
    public String getSessionKey(){
        return this.sessionKey;
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("From: \n");
        builder.append(this.hostFrom).append(" (").append(ipFrom).append(")\n");
        builder.append("To: \n").append(this.hostTo).append(" (").append(ipTo).append(")\n");
        builder.append("Date/Time: \n").append(this.timestamp).append("\n");
        if (this.messageBody != null){
            builder.append("Message Body: \n").append(this.messageBody).append("\n");
        }
        if (this.rsaKey != null){
            builder.append("RSA Key: \n").append(this.rsaKey.toString()).append("\n");
        }
        if (this.sessionKey != null){
            builder.append("Session Key: \n").append(this.sessionKey).append("\n");
        }
        
        return builder.toString();
    }
    
    public static Message fromString(String message) throws ParseException {
        String[] splitString = message.split("\\n");
        if (splitString.length < 6){
            throw new IllegalArgumentException("Not enough lines for From, To, Timestamp");
        }
        
        String[] headers = {"From: ", "To: ", "Date/Time: ", "Message Body: ", "RSA Key: ", "Session Key: "};
        if (!splitString[0].equals(headers[0])){
            throw new IllegalArgumentException("Wrong Header on line: " + splitString[0]);
        }
        String hostFrom = splitString[1].substring(0, splitString[1].indexOf(" ("));
        String ipFrom = splitString[1].substring(splitString[1].indexOf(" (") + 2, splitString[1].indexOf(")"));
        
        Builder build = new Builder(hostFrom, ipFrom);
        
        if (!splitString[2].equals(headers[1])){
            throw new IllegalArgumentException("Wrong Header on line: " + splitString[2]);
        }
        String hostTo = splitString[3].substring(0, splitString[3].indexOf(" ("));
        String ipTo = splitString[3].substring(splitString[3].indexOf(" (") + 2, splitString[3].indexOf(")"));
        build.setHostTo(hostTo);
        build.setHostIp(ipTo);

        if (!splitString[4].equals(headers[2])){
            throw new IllegalArgumentException("Wrong header on line: " + splitString[4]);
        }
        String date = splitString[5];
        build.setDate(dateFormat.parse(date));
        int next = 6;
        if (splitString.length > next && splitString[next].equals(headers[3])){
            next++;
            StringBuilder builder = new StringBuilder();
            while (splitString.length > next && !splitString[next].equals(headers[4]) && !splitString[next].equals(headers[5])){
                builder.append(splitString[next]).append("\n");
                next++;
            }
            String messageString = builder.toString();
            if (messageString.equals("")){
                build.setMessageBody(messageString);
            }
        }
        
        if (splitString.length > next && splitString[next].equals(headers[4])){
            next++;
            StringBuilder builder = new StringBuilder();
            while (splitString.length > next && !splitString[next].equals(headers[5])){
                builder.append(splitString[next]).append("\n");
                next++;
            }
            String rsaString = builder.toString();
            if (!rsaString.equals("")){
                build.setRsaKey(RsaKey.fromString(rsaString));
            }
        }
        
        if (splitString.length > next && splitString[next].equals(headers[6])){
            next++;
            StringBuilder builder = new StringBuilder();
            while (splitString.length > next && !splitString[next].equals(headers[5])){
                builder.append(splitString[next]);
                next++;
            }
            String sessionString = builder.toString();
            if (!sessionString.equals("")){
                build.setSessionKey(sessionString);
            }
        }
        
        return build.build();
        
    }
}
