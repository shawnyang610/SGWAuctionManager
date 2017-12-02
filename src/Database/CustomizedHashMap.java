package Database;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
//basically an hashmap with String key and value of 2-D ArrayLists.
//This approach allows me to link one key to many values so I can do queries later.
public class CustomizedHashMap {
	ConcurrentHashMap<String, ArrayList<ArrayList<String>>> chMap;
	//ConcurrentHashMap<String, ArrayList<String>> wholeListMap;
	public int numOfCol;
	public String[] header;
	
	public CustomizedHashMap(String[] in_header) {
	    header=in_header;
	    numOfCol=header.length;
		chMap = new ConcurrentHashMap <>();
	}
	//returns an ArrayList of ArrayLists (if any)
	public ArrayList<ArrayList<String>> getList(String key) {
		if (chMap.get(key)== null) {
			ArrayList<ArrayList<String>> emptyList = new ArrayList<ArrayList<String>>();
			ArrayList<String> emptyEntry =new ArrayList<>();
			for (int i=0; i<numOfCol; i++) {
				emptyEntry.add("EMPTY");			
			}
			emptyList.add(emptyEntry);
			return emptyList;
		}
		else
			return chMap.get(key);
	}
	
	//put the input ArrayList<String> in ArrayList<ArrayList> if key already exists,
	//otherwise create a new ArrayList<ArrayList> and put the input ArrayList<String> in it.
	public void putList (ArrayList<String> list) {
		//wholeListMap.put(list.get(0) ,list); //this list stores only one pointer to each entry.
		for (int i = 0; i<list.size();i++) {
			//set first column as primary key, so when I run a search for pk, other values dont mix in
			if (i==0) {
				ArrayList<ArrayList<String>> newList=new ArrayList<>();
				newList.add(list);
				chMap.put("PrimaryKey="+list.get(0), newList);
			}
			else if (chMap.containsKey(list.get(i))) {
					(chMap.get(list.get(i))).add(list);
			}
			else {
				ArrayList<ArrayList<String>> newList = new ArrayList<>();
				newList.add(list);
				chMap.put((String)list.get(i), newList);
			}
		}
	};

	//Append each element in the list to each element in the header and save to the save list.
    //purpose is to give each element of the list a column name it belongs to.
	public static void appendList2Header(String[] in_header, ArrayList<String> in_list){
	    if(in_header.length!=in_list.size()){
	        System.out.println("appendList2Header() error, header and list are of different sizes");
	        System.exit(1);
        }
        int i=0;
        for (String s:in_header) {
            in_list.set(i, s+"="+in_list.get(i));
            i++;
        }
    }

    public static void appendList2Header(String in_header, ArrayList<String> in_list){
        in_list.set(0, in_header+"="+in_list.get(0));
    }

	//Remember that, the first String value of the list has to be a unique Primary Key.
	public void deleteList (String inPrimaryKey) {
		if (this.getList(inPrimaryKey).isEmpty()) {
			System.out.println("Nothing to delete.");
		}
		else {
			ArrayList<String> target = this.getList(inPrimaryKey).get(0);
			for (int i=1; i<target.size(); i++) {   //for each string in the target (except the PrimaryKey)
			
				for (int j=0; j< (this.getList(target.get(i))).size();j++){ //for each ArrayList<ArrayList<String>> returned by searching with each keyword of target
					if (target == (this.getList( target.get(i))).get(j) ) { //if found target in the list
						(this.getList( (String)target.get(i))).remove(j);
					}					
				}	
			}
			this.getList(inPrimaryKey).remove(0);
			target=null;
			//return this.getAllList();

		}
	 };
	
	
	//returns a new ArrayList<ArrayList<String>> object that contains all entries in hashmap WITHOUT duplicates.
	public ArrayList<ArrayList<String>> getAllList () {
		boolean doesExist =false;
	    ArrayList<ArrayList<String>> wholeList = new ArrayList<>();
		for (ArrayList<ArrayList<String>> value : chMap.values()) {
			for (int i=0; i<value.size(); i++) {
				//check if there is existing pointer to entry in the wholeList
				for (int j=0; j<wholeList.size();j++) {
					if (wholeList.get(j)==value.get(i)) {
						doesExist=true;
						break;	
					}
				}
				if (doesExist==false) {
					wholeList.add(value.get(i));
				}
				doesExist=false;
			}
		}
		return wholeList;
	}
	public String getAvailablePKey() {
		int pk = 0;
		ArrayList<ArrayList<String>> tempList = getAllList();
		for (int i=0; i<tempList.size();i++) {
			if (pk< Integer.parseInt(tempList.get(i).get(0))) {
				pk = Integer.parseInt(tempList.get(i).get(0));
			}
		}
		tempList=null;
		pk++;
		return pk+"";
	}

	public boolean containsKey(String inKey) {
		return chMap.containsKey(inKey);
	}
	
	public void dumpToFile (String in_outfileName) throws FileNotFoundException {
		PrintWriter outFile = new PrintWriter(in_outfileName);
		String[][] dataJTable=listToString(getAllList());
		for (int i=0; i<header.length-1;i++)
			outFile.print(header[i]+" | ");
		outFile.print(header[header.length-1]);
		outFile.println("");
		for (int i=0; i<dataJTable.length;i++) {
			for (int j=0; j<dataJTable[i].length-1; j++) {
				outFile.print(dataJTable[i][j]+" | ");				
			}
			outFile.print(dataJTable[i][dataJTable[i].length-1]);
			outFile.println("");
		}
		outFile.close();
	}
	
	private String[][] listToString (ArrayList<ArrayList<String>> inList){
		String[][] newString = new String[inList.size()][];	
		for (int i=0; i<inList.size();i++) {
			newString[i]=new String[inList.get(i).size()];				
			inList.get(i).toArray(newString[i]);			
		}	
		return newString;
	}

}
