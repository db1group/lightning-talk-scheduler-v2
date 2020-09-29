package com.db1group.ltscheduler;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SchedulingEmailRequestBody implements Serializable {

    private String subject;
    private LTBody body;
    private LTDate start;
    private LTDate end;
    private LTLocation location;
    private List<LTAttendee> attendees;

    public SchedulingEmailRequestBody() {
        this.attendees = new ArrayList<>();
    }

    public SchedulingEmailRequestBody(String subject, LocalDateTime startDate, LocalDateTime endDate, String content, String attendeeEmail) {
        this();
        this.subject = subject;
        this.body = new LTBody(content);
        this.start = new LTDate(startDate);
        this.end = new LTDate(endDate);
        this.location = new LTLocation();

        attendees.add(new LTAttendee(attendeeEmail));
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LTBody getBody() {
        return body;
    }

    public void setBody(LTBody body) {
        this.body = body;
    }

    public LTDate getStart() {
        return start;
    }

    public void setStart(LTDate start) {
        this.start = start;
    }

    public LTDate getEnd() {
        return end;
    }

    public void setEnd(LTDate end) {
        this.end = end;
    }

    public LTLocation getLocation() {
        return location;
    }

    public void setLocation(LTLocation location) {
        this.location = location;
    }

    public List<LTAttendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<LTAttendee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingEmailRequestBody that = (SchedulingEmailRequestBody) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(body, that.body) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end) &&
                Objects.equals(location, that.location) &&
                Objects.equals(attendees, that.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, body, start, end, location, attendees);
    }

    private class LTBody implements Serializable {

        private String contentType;
        private String content;

        public LTBody() {
        }

        public LTBody(String content) {
            this.contentType = "HTML";
            this.content = content;
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
            LTBody ltBody = (LTBody) o;
            return Objects.equals(contentType, ltBody.contentType) &&
                    Objects.equals(content, ltBody.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(contentType, content);
        }
    }

    private class LTDate implements Serializable {

        private String dateTime;
        private String timeZone;

        public LTDate() {
        }

        public LTDate(LocalDateTime dateTime) {
            this.dateTime = dateTime.toString();
            this.timeZone = "UTC";
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LTDate ltDate = (LTDate) o;
            return Objects.equals(dateTime, ltDate.dateTime) &&
                    Objects.equals(timeZone, ltDate.timeZone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateTime, timeZone);
        }
    }

    private class LTLocation implements Serializable {
        private String displayName;

        public LTLocation() {
            this.displayName = "Transmissão no YouTube";
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LTLocation that = (LTLocation) o;
            return Objects.equals(displayName, that.displayName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(displayName);
        }
    }

    private class LTAttendee implements Serializable {

        private LTEmailAddress emailAddress;
        private String type;

        public LTAttendee() {
        }

        public LTAttendee(String emailAddress) {
            this.emailAddress = new LTEmailAddress(emailAddress);
            this.type = "required";
        }

        public LTEmailAddress getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(LTEmailAddress emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LTAttendee that = (LTAttendee) o;
            return Objects.equals(emailAddress, that.emailAddress) &&
                    Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(emailAddress, type);
        }
    }

    private class LTEmailAddress implements  Serializable{

        private String address;

        public LTEmailAddress() {
        }

        public LTEmailAddress(String address) {
            this.address = address;
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
            LTEmailAddress that = (LTEmailAddress) o;
            return Objects.equals(address, that.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(address);
        }
    }

    @Override
    public String toString() {
        String messagePattern = "SchedulingEmailRequestBody{subject: %s, attendee: %s}";
        return String.format(messagePattern, this.subject, getEmailAddress());
    }

    private String getEmailAddress() {
        return this.attendees.get(0).emailAddress.address;
    }
}
