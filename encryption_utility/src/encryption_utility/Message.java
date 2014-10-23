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
        private String hostFrom;
        private String ipFrom;
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
        
        private Builder (){
            
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
    //TODO: check validity of message
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
        StringBuilder builder = new StringBuilder();
        builder.append("From: ").append(this.hostFrom);
        builder.append("\ufffd").append("From IP: ").append(this.ipFrom);
        builder.append("\ufffd").append("To: ").append(this.hostTo);
        builder.append("\ufffd").append("To IP: ").append(this.ipTo);
        builder.append("\ufffd").append("Date/Time: ").append(this.timestamp);
        if (this.messageBody != null){
            builder.append("\ufffd").append("Message Body: ").append(this.messageBody);
        }
        if (this.rsaKey != null){
            builder.append("\ufffd").append("RSA Key: ").append("\ufffd").append(this.rsaKey.toString()).append("\ufffd").append("RSA End");
        }
        if (this.sessionKey != null){
            builder.append("\fffd").append("Session Key: ").append(this.sessionKey);
        }
        
        return builder.toString();
    }
    
    protected static Message fromString(String[] splitStrings, int start) throws ParseException{
        String[] headers = {"From: ", "From IP: ", "To: ", "To IP: ", "Date/Time: ", "Message Body: ", "RSA Key: ", "Session Key: "};
        int counted = 0;
        Builder builder = new Builder();
        for (int i = start; i < splitStrings.length; i++){
            String current = splitStrings[i];
            int index = current.indexOf(headers[counted]);
            String value = current.substring(index+headers[counted].length());
            if (index == 0 && counted <= 4){
                throw new ParseException("Failed to parse the message at line", i);
            }
            else if (index == 0){
                counted++;
                i--;
                continue;
            }
            switch (headers[counted]){
                case "From: ":
                    builder.hostFrom = value;
                    break;
                case "From IP: ":
                    builder.ipFrom = value;
                    break;
                case "To: ":
                    builder.hostTo = value;
                    break;
                case "To IP: ":
                    builder.ipTo = value;
                    break;
                case "Date/Time: ":
                    builder.date = dateFormat.parse(value);
                    break;
                case "Message Body: ":
                    builder.messageBody = value;
                    break;
                case "RSA Key: ":
                    i++;
                    builder.rsaKey = RsaKey.fromSerialString(splitStrings, i);
                    while (splitStrings[i].indexOf("RSA End") == -1){
                        i++;
                    }
                    break;
                case "Session Key: ":
                    builder.sessionKey = value;
                    break;
            }
            counted++;
            if (counted >= headers.length){
                return builder.build();
            }
        }
        return builder.build();
    }
    
    public static Message fromString(String message) throws ParseException {
        return fromString(message.split("\ufffd"), 0);
    }
}
