package cc.explain.client;


import com.google.gwt.core.client.GWT;

//TODO refactor: nie wiem jak:  przerobic ten caly koncept z dialogname, moze zapostowac gdzies na jakies forum i zapytac jak to przerobic.
public enum DialogName {

    REGISTER(RegisterDialog.class),

    EXCLUDE_WORDS(ExcludeWordsDialog.class),

    INCLUDE_WORDS(IncludeWordsDialog.class),

    ADD_TEXT(AddTextDialog.class),

    OPTIONS(OptionsDialog.class),

    CONTACT(ContactDialog.class),

    WATCH(WatchDialog.class),

    RESET(ResetDialog.class);


    private Class<?> clazz;

    private DialogName(Class<?> clazz) {
        this.clazz = clazz;
    }

    public CafaWidget getDialog() {            //TODO refactor.
        if (REGISTER.equals(this)) {
			return GWT.create(RegisterDialog.class);
		} else if (EXCLUDE_WORDS.equals(this)) {
			return GWT.create(ExcludeWordsDialog.class);
        } else if(ADD_TEXT.equals(this)){
           return GWT.create(AddTextDialog.class);
        } else if(OPTIONS.equals(this)){
           return GWT.create(OptionsDialog.class);
        } else if(INCLUDE_WORDS.equals(this)){
           return GWT.create(IncludeWordsDialog.class);
        } else if(CONTACT.equals(this)){
           return GWT.create(ContactDialog.class);
        }else if(WATCH.equals(this)){
           return GWT.create(WatchDialog.class);
        }else if(RESET.equals(this)){
            return GWT.create(ResetDialog.class);
        }
        return GWT.create(AddTextDialog.class);
    }

    public String getName() {
        return name();
    }

}