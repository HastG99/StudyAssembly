package ru.studyasm;

public class Section {

    private final String name;
    private final long start;
    private long end = 0;


    public Section(String name, long start) {
        this.name = name;
        this.start = start;
    }


    public boolean contains(long n) {
        return n > start && n < end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }
}
