package cc.explain.server.subtitle;

/**
 * User: kzimnick
 * Date: 28.05.13
 * Time: 21:52
 */
public enum SubtitleProcessor {

    IN_TEXT(new InTextTranslationSubtitleStrategy()),
    ONLY_TRANSLATION(new OnlyTranslationsSubtitleStrategy()),
    LONG_DISPLAY(new LongDisplayTranslationSubtitleStrategy())
    ;

    private SubtitleStrategy strategy;

    private SubtitleProcessor(SubtitleStrategy strategy){
        this.strategy = strategy;
    }

    public SubtitleStrategy getStrategy(){
        return strategy;
    }
}
