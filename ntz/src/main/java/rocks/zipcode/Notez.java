package rocks.zipcode;

import rocks.zipcode.FileMap;
import rocks.zipcode.NoteList;

/**
 * ntz main command.
 */
public final class Notez {

    private FileMap filemap;

    public Notez() {
        this.filemap  = new FileMap();
    }
    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        boolean _debug = true;

        if (_debug) {
            System.err.print("Args: [");
            for (String a : args) {
                System.err.print(a+" ");
            }
            System.err.println("]");
        }

        Notez ntzEngine = new Notez();

        ntzEngine.loadDatabase();
        

        if (args.length == 0) {
            ntzEngine.printResults();
        } else {
            if (args[0].equals("-r")) {
                ntzEngine.addToCategory("General", args);
            }
            else if(args[0].equals("-c")){
                ntzEngine.createCategory(args);
            }
            else if(args[0].equals("-e")){
                ntzEngine.editNote(args);
            }
            else if(args[0].equals("-f")){
                ntzEngine.forget(args);
            }
        }
        ntzEngine.saveDatabase();
        ntzEngine.printResults();

    }

    private void saveDatabase() {
        filemap.save();
    }

    private void loadDatabase() {
        filemap.load();
    }

    public void printResults() {
        System.out.println(this.filemap.toString());
    }

    public void loadDemoEntries() {
        filemap.put("General", new NoteList("The Very first Note"));
        filemap.put("note2", new NoteList("A secret second note"));
        filemap.put("category3", new NoteList("Did you buy bread AND eggs?"));
        filemap.put("anotherNote", new NoteList("Hello from ZipCode!"));
    }

    public boolean addToCategory(String string, String[] argv) {
        if(filemap.containsKey(string)){
            filemap.get(string).add(argv[1]);
            return true;
        }
        else{
            NoteList newNote = new NoteList(argv[1]);
            filemap.put(string, newNote);
            return true;
        }
    }

    public boolean createCategory(String[] args){
        NoteList noteList = new NoteList(args[2]);
        filemap.put(args[1], noteList);
        return true;
    }

    public boolean editNote(String[] args){

        if(filemap.containsKey(args[1])){
            NoteList noteList = filemap.get(args[1]);
            int noteNumber = Integer.parseInt(args[2]) - 1;
            noteList.set(noteNumber, args[3]);
            filemap.put(args[1], noteList);
            return true;
        }
        return false;
    }

    public boolean forget(String[] args) {

        if(filemap.containsKey(args[1])){
            NoteList noteList = filemap.get(args[1]);
            noteList.remove(noteList.get(Integer.parseInt(args[2]) - 1));
            if(noteList.isEmpty()){
                filemap.remove(args[1]);
            }
            else{
                filemap.put(args[1], noteList);
            }
            return true;
        }
        return false;
    }

}
