package me.espere.feelings.spec.dictionary;

import java.util.Optional;

public interface Dictionary {
    Optional<Entry> getEntry(String word);
}
