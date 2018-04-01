package me.espere.feelings.spec.lemmatizer;

import java.util.Collection;

public interface Lemmatizer {
    Collection<String> lemmas(String sentence);
}
