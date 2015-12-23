package org.apache.log4j;

class CategoryKey {

    String name;
    int hashCache;

    CategoryKey(String name) {
        this.name = name;
        hashCache = name.hashCode();
    }

    final public int hashCode() {
        return hashCache;
    }

    final public boolean equals(Object rArg) {
        if (this == rArg)
            return true;

        if (rArg != null && CategoryKey.class == rArg.getClass())
            return name.equals(((CategoryKey) rArg).name);
        else
            return false;
    }
    @Override
    public String toString() {
        return name+"/"+hashCache;
    }
}