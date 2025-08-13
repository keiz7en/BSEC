package com.bsec.oop.SiamShikder.Regulator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Represents a single IPO application entry for the review workflow.
 * <p>
 * This model uses JavaFX properties so it binds cleanly to TableView columns.
 */
public class IPOApplication {

    private final StringProperty applicationId = new SimpleStringProperty(this, "applicationId", "");
    private final StringProperty companyName = new SimpleStringProperty(this, "companyName", "");
    private final ObjectProperty<LocalDate> submissionDate = new SimpleObjectProperty<>(this, "submissionDate", null);
    private final StringProperty complianceStatus = new SimpleStringProperty(this, "complianceStatus", "");
    private final StringProperty reviewDecision = new SimpleStringProperty(this, "reviewDecision", "Pending");

    // Optional descriptive fields, useful for audits or export
    private final StringProperty reviewerName = new SimpleStringProperty(this, "reviewerName", "");
    private final StringProperty comments = new SimpleStringProperty(this, "comments", "");

    public IPOApplication() {
    }

    public IPOApplication(
            String applicationId,
            String companyName,
            LocalDate submissionDate,
            String complianceStatus,
            String reviewDecision,
            String reviewerName,
            String comments
    ) {
        setApplicationId(applicationId);
        setCompanyName(companyName);
        setSubmissionDate(submissionDate);
        setComplianceStatus(complianceStatus);
        setReviewDecision(reviewDecision);
        setReviewerName(reviewerName);
        setComments(comments);
    }

    public String getApplicationId() {
        return applicationId.get();
    }

    public void setApplicationId(String value) {
        applicationId.set(value);
    }

    public StringProperty applicationIdProperty() {
        return applicationId;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String value) {
        companyName.set(value);
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate.get();
    }

    public void setSubmissionDate(LocalDate value) {
        submissionDate.set(value);
    }

    public ObjectProperty<LocalDate> submissionDateProperty() {
        return submissionDate;
    }

    public String getComplianceStatus() {
        return complianceStatus.get();
    }

    public void setComplianceStatus(String value) {
        complianceStatus.set(value);
    }

    public StringProperty complianceStatusProperty() {
        return complianceStatus;
    }

    public String getReviewDecision() {
        return reviewDecision.get();
    }

    public void setReviewDecision(String value) {
        reviewDecision.set(value);
    }

    public StringProperty reviewDecisionProperty() {
        return reviewDecision;
    }

    public String getReviewerName() {
        return reviewerName.get();
    }

    public void setReviewerName(String value) {
        reviewerName.set(value);
    }

    public StringProperty reviewerNameProperty() {
        return reviewerName;
    }

    public String getComments() {
        return comments.get();
    }

    public void setComments(String value) {
        comments.set(value);
    }

    public StringProperty commentsProperty() {
        return comments;
    }
}
