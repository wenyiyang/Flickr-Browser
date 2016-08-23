package org.example.android.flickrbrowser;

/**
 * Class used to store parsed data which represents info of a photo.
 */
public class Photo {

    private String title;
    private String author;
    private String authorId;
    private String link;
    private String tags;
    private String image;

    public Photo(String title, String author, String authorId, String link, String tags, String image) {
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.link = link;
        this.tags = tags;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getLink() {
        return link;
    }

    public String getTags() {
        return tags;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", AuthorId='" + authorId + '\'' +
                ", Link='" + link + '\'' +
                ", Tags='" + tags + '\'' +
                ", Image='" + image + '\'' +
                '}';
    }
}