package semanticanalysis;

import ast.types.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing a symbol table. It's a list of hash table symbol table. We
 * keep track of a ArrayList of HashMap called `symbolTable` and an array of
 * integer called `offset`.
 */
public class SymbolTable {

    private final ArrayList<HashMap<String, STentry>> symbolTable;
    private final ArrayList<Integer> offset;

    public SymbolTable() {
        this.symbolTable = new ArrayList<>();
        this.offset = new ArrayList<>();
    }

    /**
     * Returns the nesting level.
     */
    public Integer nesting() {
        return this.symbolTable.size() - 1;
    }

    /**
     * Check out if an `id` is into the symbol table. Returns an STentry object
     * or null if the `id` is not found.
     *
     * @param id is the identifier of the STentry to find.
     */
    public STentry lookup(String id) {
        int n = this.symbolTable.size() - 1;
        boolean found = false;
        STentry T = null;
        while ((n >= 0) && !found) {
            HashMap<String, STentry> H = this.symbolTable.get(n);
            T = H.get(id);
            if (T != null) {
                found = true;
            } else {
                n = n - 1;
            }
        }
        return T;
    }

    /**
     * Return the position of a STentry given the `id`, if it exists. Otherwise
     * return `-1`. We start the search from the last inserted hashmap.
     *
     * @param id is the identifier of the STentry to find.
     */
    public Integer nslookup(String id) {
        int n = this.symbolTable.size() - 1;
        boolean found = false;
        while ((n >= 0) && !found) {
            HashMap<String, STentry> H = this.symbolTable.get(n);
            if (H.get(id) != null) {
                found = true;
            } else {
                n = n - 1;
            }
        }
        return n;
    }

    /**
     * Add an hashmap to the given symbol table and increase the offset level.
     * We start from 2 because we have FP and AL before all.
     *
     * @param H is an hashmap that is must be added into the symbol table
     */
    public void add(HashMap<String, STentry> H) {
        this.symbolTable.add(H);
        this.offset.add(1);
    }

    /**
     * Remove the last level for the symbol table.
     */
    public void remove() {
        int x = this.symbolTable.size();
        this.symbolTable.remove(x - 1);
        this.offset.remove(x - 1);
    }

    /**
     * Return `true` if the `id` is present in the last inseted hashmap.
     * Otherwise return `false`.
     *
     * @param id is the identifier of the STentry to find.
     */
    public boolean top_lookup(String id) {
        int n = symbolTable.size() - 1;
        STentry T;
        HashMap<String, STentry> H = symbolTable.get(n);
        T = H.get(id);
        return (T != null);
    }

    /**
     * Insert a new entry into the symbol table.
     *
     * @param id
     * @param type
     * @param _nesting
     * @param _label
     */
    public void insert(String id, Type type, int _nesting, String _label) {
        int n = symbolTable.size() - 1;
        HashMap<String, STentry> H = this.symbolTable.get(n);
        this.symbolTable.remove(n);

        int offs = this.offset.get(n);
        this.offset.remove(n);

        STentry idtype = new STentry(type, offs, _nesting, _label);
        H.put(id, idtype);

        this.symbolTable.add(H);

        // We always increment the offset by 1 otherwise we need ad-hoc bytecode
        // operations
        offs = offs + 1;

        this.offset.add(offs);

    }

    /**
     * Increase the offset level.
     */
    public void increaseoffset() {
        int n = this.offset.size() - 1;
        int offs = this.offset.get(n);
        this.offset.remove(n);

        offs = offs + 1;

        this.offset.add(offs);
    }

    @Override
    public String toString() {
        // Print the symbol table
        String str = "";
        for (int i = 0; i < this.symbolTable.size(); i++) {
            str += "Level " + i + "\n";
            HashMap<String, STentry> H = this.symbolTable.get(i);
            for (String key : H.keySet()) {
                STentry T = H.get(key);
                str += key + " -> " + T.toString() + "\n";
            }
        }
        return str;
    }

}
