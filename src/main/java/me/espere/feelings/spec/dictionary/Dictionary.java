package me.espere.feelings.spec.dictionary;

import me.espere.feelings.spec.VadValue;

import java.util.Optional;

public interface Dictionary {
    Optional<Entry> getEntry(String word);

    VadValue getMeanVadValue();
}
