package me.espere.feelings.spec.lemmatizer;

import java.util.Collection;

public interface Lemmatizer {
    Collection<Lemma> lemmas(String text);
}
