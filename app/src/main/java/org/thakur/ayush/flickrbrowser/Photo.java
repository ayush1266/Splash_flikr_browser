package org.thakur.ayush.flickrbrowser;

import java.io.Serializable;

/**
 * Created by win on 16-05-2018.
 */

class Photo implements Serializable {
    private static final long serailVersionUID = 1L;

    private String mTitle;
    private String mAuthorId;
    private String mAuthor;
    private String mLink;
    private String mTags;
    private String mImage;

    public Photo(String mTitle, String mAuthorId, String mAuthor, String mLink, String mTags, String mImage) {
        this.mTitle = mTitle;
        this.mAuthorId = mAuthorId;
        this.mAuthor = mAuthor;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mImage = mImage;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthorId() {
        return mAuthorId;
    }

     String getAuthor() {
        return mAuthor;
    }

     String getLink() {
        return mLink;
    }

     String getTags() {
        return mTags;
    }

     String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                "}\n";
    }
}
