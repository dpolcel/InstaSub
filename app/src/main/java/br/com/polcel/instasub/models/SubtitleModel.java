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
    private Date mCreated;
    private Date mDeleted;
    public static List<SubtitleModel> mSubtitleModelList;

    public int lastSubtitleId = 0;

    public SubtitleModel() {

    }

    public SubtitleModel(String title, String description, Date created, Date deleted) {
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

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        this.mCreated = created;
    }

    public Date getDeleted() {
        return mDeleted;
    }

    public void setDeleted(Date deleted) {
        this.mDeleted = deleted;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }
}
