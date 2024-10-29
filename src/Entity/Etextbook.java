package Entity;

public class Etextbook {
    private Integer id;
    private String title;

    // Constructors
    public Etextbook() {}

    public Etextbook(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Method to display e-textbook details (for debugging purposes)
    @Override
    public String toString() {
        return "Etextbook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
