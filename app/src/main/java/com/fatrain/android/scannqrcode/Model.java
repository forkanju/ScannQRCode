package com.fatrain.android.scannqrcode;

public class Model {
    private String txtResult;

    Model() {}

    public Model(String txtResult) {
        this.txtResult = txtResult;
    }

    public String getTxtResult() {
        return txtResult;
    }

    public void setTxtResult(String txtResult) {
        this.txtResult = txtResult;
    }
}
