package com.db1group.ltscheduler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransmissionEmailRequestBody implements Serializable {

    private Message message;
    private Boolean saveToSentItems;

    public TransmissionEmailRequestBody(String recipient, String subject, String content) {
        this.saveToSentItems = true;
        this.message = new Message(recipient, subject, content);
    }

    public TransmissionEmailRequestBody() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Boolean isSaveToSentItems() {
        return saveToSentItems;
    }

    public void setSaveToSentItems(Boolean saveToSentItems) {
        this.saveToSentItems = saveToSentItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransmissionEmailRequestBody that = (TransmissionEmailRequestBody) o;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        String messagePattern = "TransmissionEmailRequestBody{ recipient: %s; subject: %s; content: %s; }";
        return String.format(messagePattern, message.getRecipient(), message.getSubject(), message.getBody().getContent());
    }

    private class Message implements Serializable {

        private String subject;
        private Body body;
        private List<Recipient> toRecipients;

        public Message() {}

        public Message(String recipient, String subject, String content) {
            this.subject = subject;
            this.body = new Body(content);

            this.toRecipients = new ArrayList<>();
            this.toRecipients.add(new Recipient(recipient));
        }

        public String getRecipient() {
            return this.toRecipients.get(0).getEmailAddress().getAddress();
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        public List<Recipient> getToRecipients() {
            return toRecipients;
        }

        public void setToRecipients(List<Recipient> toRecipients) {
            this.toRecipients = toRecipients;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Message message = (Message) o;
            return subject.equals(message.subject) &&
                    body.equals(message.body) &&
                    toRecipients.equals(message.toRecipients);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subject, body, toRecipients);
        }
    }

    private class Body implements Serializable {
        private String contentType;
        private String content;

        public Body(String content) {
            this.contentType = "Text";
            this.content = content;
        }

        public Body() {
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Body body = (Body) o;
            return Objects.equals(content, body.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(content);
        }
    }

    private class Recipient implements Serializable {

        private EmailAddress emailAddress;

        public Recipient() {
        }

        public Recipient(String address) {
            this.emailAddress = new EmailAddress(address);
        }

        public EmailAddress getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(EmailAddress emailAddress) {
            this.emailAddress = emailAddress;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Recipient recipient = (Recipient) o;
            return Objects.equals(emailAddress, recipient.emailAddress);
        }

        @Override
        public int hashCode() {
            return Objects.hash(emailAddress);
        }
    }

    private class EmailAddress implements Serializable {

        private String address;

        public EmailAddress(String address) {
            this.address = address;
        }

        public EmailAddress() {
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmailAddress that = (EmailAddress) o;
            return Objects.equals(address, that.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(address);
        }
    }
}
