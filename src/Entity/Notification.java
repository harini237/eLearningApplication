package Entity;

public class Notification {
    private String id;
    private String user_id;
    private String message;
    private String status;

    public Notification () {
    }

    public Notification(String id, String user_id, String message, String status) {
        this.id = id;
        this.user_id = user_id;
        this.message = message;
        this.status = status;
    }

    public Notification(String id, String user_id, String message) {
        this.id = id;
        this.user_id = user_id;
        this.message = message;
        this.status = "unread";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
