package com.basit.pdfviewer;

public class PdfClass {

    private String pdfName  , pdfUrl , unit ;

    public PdfClass(String pdfName, String pdfUrl, String unit) {
        this.pdfName = pdfName;
        this.pdfUrl = pdfUrl;
        this.unit = unit;
    }



    public PdfClass() {
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
