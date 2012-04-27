package com.mySampleApplication.client;


import com.google.gwt.core.client.GWT;

//TODO refactor: nie wiem jak:  przerobic ten caly koncept z dialogname, moze zapostowac gdzies na jakies forum i zapytac jak to przerobic.
public enum DialogName {

    REGISTER(RegisterDialog.class),

    EXCLUDE_WORDS(ExcludeWordsDialog.class);

    private Class<?> clazz;

    private DialogName(Class<?> clazz) {
        this.clazz = clazz;
    }

    public CafaWidget getDialog() {
        if (REGISTER.equals(this)) {
			return GWT.create(RegisterDialog.class);
		} else if (EXCLUDE_WORDS.equals(this)) {
			return GWT.create(ExcludeWordsDialog.class);
        }
        return GWT.create(RegisterDialog.class);
    }

    public String getName() {
        return name();
    }

}