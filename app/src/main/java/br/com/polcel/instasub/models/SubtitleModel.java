package br.com.polcel.instasub.models;

import java.util.Date;
import java.util.List;

/**
 * Created by polcel on 24/03/17.
 */

public class SubtitleModel {

    private Long mId;
    private String mTitle;
    private String mDescription;
    private Long mCreated;
    private Long mDeleted;
    public static List<SubtitleModel> mSubtitleModelList;

    public int lastSubtitleId = 0;

    public SubtitleModel() {

    }

    public SubtitleModel(String title, String description, Long created, Long deleted) {
        this.mTitle = title;
        this.mDescription = description;
        this.mCreated = created;
        this.mDeleted = deleted;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Long getCreated() {
        return mCreated;
    }

    public void setCreated(Long created) {
        this.mCreated = created;
    }

    public Long getDeleted() {
        return mDeleted;
    }

    public void setDeleted(Long deleted) {
        this.mDeleted = deleted;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }
}
