package cc.explain.lucene; /**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * Filters {@link org.apache.lucene.analysis.standard.StandardTokenizer} with {@link org.apache.lucene.analysis.standard.StandardFilter}, {@link
 * org.apache.lucene.analysis.LowerCaseFilter} and {@link org.apache.lucene.analysis.StopFilter}, using a list of
 * English stop words.
 * <p/>
 * <a name="version"/>
 * <p>You must specify the required {@link org.apache.lucene.util.Version}
 * compatibility when creating StandardAnalyzer:
 * <ul>
 * <li> As of 2.9, StopFilter preserves position
 * increments
 * <li> As of 2.4, Tokens incorrectly identified as acronyms
 * are corrected (see <a href="https://issues.apache.org/jira/browse/LUCENE-1068">LUCENE-1608</a>
 * </ul>
 */
public class StandardAnalyzerWithoutLowerCase extends Analyzer {
    private Set<?> stopSet;

    /**
     * Specifies whether deprecated acronyms should be replaced with HOST type.
     */
    private final boolean replaceInvalidAcronym, enableStopPositionIncrements;

    /**
     * An unmodifiable set containing some common English words that are usually not
     * useful for searching.
     */
    public static final Set<?> STOP_WORDS_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
    private final Version matchVersion;

    /**
     * Builds an analyzer with the default stop words ({@link
     * #STOP_WORDS_SET}).
     *
     * @param matchVersion Lucene version to match See {@link
     *                     <a href="#version">above</a>}
     */
    public StandardAnalyzerWithoutLowerCase(Version matchVersion) {
        this(matchVersion, STOP_WORDS_SET);
    }

    /**
     * Builds an analyzer with the given stop words.
     *
     * @param matchVersion Lucene version to match See {@link
     *                     <a href="#version">above</a>}
     * @param stopWords    stop words
     */
    public StandardAnalyzerWithoutLowerCase(Version matchVersion, Set<?> stopWords) {
        stopSet = stopWords;
        setOverridesTokenStreamMethod(StandardAnalyzerWithoutLowerCase.class);
        enableStopPositionIncrements = StopFilter.getEnablePositionIncrementsVersionDefault(matchVersion);
        replaceInvalidAcronym = matchVersion.onOrAfter(Version.LUCENE_24);
        this.matchVersion = matchVersion;
    }

    /**
     * Builds an analyzer with the stop words from the given file.
     *
     * @param matchVersion Lucene version to match See {@link
     *                     <a href="#version">above</a>}
     * @param stopwords    File to read stop words from
     * @see org.apache.lucene.analysis.WordlistLoader#getWordSet(java.io.File)
     */
    public StandardAnalyzerWithoutLowerCase(Version matchVersion, File stopwords) throws IOException {
        this(matchVersion, WordlistLoader.getWordSet(stopwords));
    }

    /**
     * Builds an analyzer with the stop words from the given reader.
     *
     * @param matchVersion Lucene version to match See {@link
     *                     <a href="#version">above</a>}
     * @param stopwords    Reader to read stop words from
     * @see org.apache.lucene.analysis.WordlistLoader#getWordSet(java.io.Reader)
     */
    public StandardAnalyzerWithoutLowerCase(Version matchVersion, Reader stopwords) throws IOException {
        this(matchVersion, WordlistLoader.getWordSet(stopwords));
    }

    /**
     * Constructs a {@link org.apache.lucene.analysis.standard.StandardTokenizer} filtered by a {@link
     * org.apache.lucene.analysis.standard.StandardFilter}, a {@link org.apache.lucene.analysis.LowerCaseFilter} and a {@link org.apache.lucene.analysis.StopFilter}.
     */
    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        StandardTokenizer tokenStream = new StandardTokenizer(matchVersion, reader);
        tokenStream.setMaxTokenLength(maxTokenLength);
        TokenStream result = new StandardFilter(tokenStream);
        result = new StopFilter(enableStopPositionIncrements, result, stopSet);
        return result;
    }

    private static final class SavedStreams {
        StandardTokenizer tokenStream;
        TokenStream filteredTokenStream;
    }

    /**
     * Default maximum allowed token length
     */
    public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;

    private int maxTokenLength = DEFAULT_MAX_TOKEN_LENGTH;

    /**
     * Set maximum allowed token length.  If a token is seen
     * that exceeds this length then it is discarded.  This
     * setting only takes effect the next time tokenStream or
     * reusableTokenStream is called.
     */
    public void setMaxTokenLength(int length) {
        maxTokenLength = length;
    }

    /**
     * @see #setMaxTokenLength
     */
    public int getMaxTokenLength() {
        return maxTokenLength;
    }

    @Override
    public TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
        if (overridesTokenStreamMethod) {
            // LUCENE-1678: force fallback to tokenStream() if we
            // have been subclassed and that subclass overrides
            // tokenStream but not reusableTokenStream
            return tokenStream(fieldName, reader);
        }
        SavedStreams streams = (SavedStreams) getPreviousTokenStream();
        if (streams == null) {
            streams = new SavedStreams();
            setPreviousTokenStream(streams);
            streams.tokenStream = new StandardTokenizer(matchVersion, reader);
            streams.filteredTokenStream = new StandardFilter(streams.tokenStream);
            streams.filteredTokenStream = new StopFilter(enableStopPositionIncrements,
                    streams.filteredTokenStream, stopSet, true);

        } else {
            streams.tokenStream.reset(reader);
        }
        streams.tokenStream.setMaxTokenLength(maxTokenLength);

        streams.tokenStream.setReplaceInvalidAcronym(replaceInvalidAcronym);

        return streams.filteredTokenStream;
    }
}
