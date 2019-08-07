
class bst{
    
    Node root;
    

    private class Node{
    	
    	// These attributes of the Node class will not be sufficient for those attempting the AVL extra credit.
    	// You are free to add extra attributes as you see fit, but do not remove attributes given as it will mess with help code.
        String keyword;
        Record record;
        int size;
        Node l = null;
        Node r = null;
        
        private Node(String k){
        	// TODO Instantialize a new Node with keyword k.
        	setKeyword(k);
        	size = 0;
        }
        
        public void setKeyword(String keyword) {
        	this.keyword = keyword;
        }
        
        public String getKeyword() {
        	return this.keyword;
        }
        
        public void setRecord(Record recordEntry) {
        	this.record = recordEntry;
        }
        
        private void update(Record r){
        	//TODO Adds the Record r to the linked list of records. You do not have to check if the record is already in the list.
        	//HINT: Add the Record r to the front of your linked list.
        	r.next = this.record;
        	this.record = r;
        	this.size++;
        	//Done
        }

    }

    public bst(){
        this.root = null;
    }
    
    // Recursive function used by other functions as Dr. Khan told us to make this instead of altering the other functions
    private Node traverse(String keyword, Node currNode) {
    	// either returns an empty node or the node itself
    	
    	// checks to see if input node is null or not
    	// base case
    	if (currNode == null) {
    		System.out.println("Input node is null");
    		return null;
    	}
    	
    	// input node is the keyword node
    	if ((keyword.compareTo(currNode.keyword)) == 0)
    		return currNode;
    	
    	// keyword is greater than node keyword
    	if ((keyword.compareTo(currNode.keyword)) > 0) {
    		// returns currnode's right child if it's empty
    		if (currNode.r == null)
    			return currNode.r;
    		// traverses again with right child
    		return traverse(keyword, currNode.r);
    	}
    	else {
    	// neither of former cases means ((keyword.compareTo(currNode.keyword)) < 0) {
    		// returns currnode's left child if it's empty
    		if (currNode.l == null)
    			return currNode.l;
    		// traverses again with left child
    		return traverse(keyword, currNode.l);
    	}
    	
    }
     
	public void insert(String keyword, FileData fd){

        Record recordToAdd = new Record(fd.id, fd.author, fd.title, null);
        //TODO Write a recursive insertion that adds recordToAdd to the list of records for the node associated
        //with keyword. If there is no node, this code should add the node.
        
        // gets rid of white space around keyword
        keyword = keyword.trim();
        
        // calls a private recursive function
        insertRecursive(keyword, recordToAdd, root);
    }

	// recursive function called in insert function
	private void insertRecursive(String keyword, Record recordToAdd, Node currNode) {
		// checks base case if the node is empty
		// case 1
		if (currNode == null) {
			// creates new node and becomes the current node
			Node newNode = new Node(keyword);
			newNode.update(recordToAdd);
			this.root = newNode;
			
		} 
		// case 2
		// checks to see if keyword is greater than the node keyword
		else if ((keyword.compareTo(currNode.keyword)) > 0) {
			
			// checks if right child exists
			if (currNode.r != null) {
				insertRecursive(keyword, recordToAdd, currNode.r);
			} 
			// right child is null so it creates that as the new node 
			else {
				Node newNode = new Node(keyword);
				newNode.update(recordToAdd);
				currNode.r = newNode;
			}
		} 
		// case 3
		// checks to see if keyword is less than the node keyword		
		else if ((keyword.compareTo(currNode.keyword)) < 0) {
			
			// checks if left child exists
			if (currNode.l != null) {
				insertRecursive(keyword, recordToAdd, currNode.l);
			} 
			// left child is null so it creates that as the new node
			else {
				Node newNode = new Node(keyword);
				newNode.update(recordToAdd);
				currNode.l = newNode;
			}
		} 
		// case 4
		// node exists and needs to be updated with the new record
		else {
			currNode.update(recordToAdd);
		}
	}

    public boolean contains(String keyword){
    	//TODO Write a recursive function which returns true if a particular keyword exists in the bst
    	Node resultNode = traverse(keyword, root);
    	if (resultNode == null)
    		return false;
    	else
    		return true;
    }

    public Record get_records(String keyword) {
    	//TODO Returns the first record for a particular keyword. This record will link to other records
    	//If the keyword is not in the bst, it should return null.
    	
    	// base case if tree is empty
    	if (root == null) {
    		System.out.println("Tree is empty. No records.");
    		return null;
    	}
    	
    	// tree is not empty, will look for the node with the keyword
    	// starts with currNode at root and searches for the node location
    	Node currNode = traverse(keyword, root);
    	
    	// keyword's location in tree is null
    	if (currNode.record == null) {
    		System.out.println("Record not found");
    		return null;
    	}
    	// keyword location is not null
    	else {
    		return currNode.record;
    	}
	}

    public void delete(String keyword) {
    	//TODO Write a recursive function which removes the Node with keyword from the binary search tree.
    	//You may not use lazy deletion and if the keyword is not in the bst, the function should do nothing.
    	
    	// no need to trim keyword as it would've been done in insertion
    	// calls a private recursive function
		deleteRecursive(keyword, root);
	}
	
    // recursive function called in delete function
	private Node deleteRecursive(String keyword, Node currNode){
		
		// base case where the input node is null
		if (currNode == null){
			// the keyword doesn't have a node
			System.out.println("Keyword not found");
		} 
		// keyword is greater than current node's keyword
		else if (keyword.compareTo(currNode.keyword) > 0) {
			// calls itself with right child
			currNode.r = deleteRecursive(keyword, currNode.r);
		} 
		// keyword is less than current node's keyword
		else if (keyword.compareTo(currNode.keyword) < 0) {
			// calls itself with left child
			currNode.l = deleteRecursive(keyword, currNode.l);
		} 
		// remaining case is where keyword matches with current node keyword
		else {
			// base case of if node was a leaf
			if (currNode.l == null && currNode.r == null) {
				// no children to worry about, node is erased
				currNode = null;
			} 
			// case where node has no right child but has left child
			else if (currNode.l != null && currNode.r == null) {
				// currNode becomes left child
				currNode = currNode.l;
			}
			// case where node has no left child but has right child
			else if (currNode.l == null && currNode.r != null) {
				// currNode becomes right child
				currNode = currNode.r;
			}
			// remaining case where node has both children
			else {
				// locate and create the replacement node as greatest value of left subtree
				Node replacement = new Node(leftMax(currNode.l).keyword);
				replacement.record = leftMax(currNode.l).record;
				replacement.size = leftMax(currNode.l).size;
				// delete the original node copied for replacement
				delete(replacement.keyword);
				// copies all elements of leftMax replacement node to the current node to overwrite
				currNode.keyword = replacement.keyword;
				currNode.record = replacement.record;
				currNode.size = replacement.size;
			}
		}
		return currNode;
	}
	
	// private method to locate max value of left tree
	private Node leftMax(Node currNode) {
		// base case of null input
		if (currNode == null) {
			return null;
		}
		// currNode is max value and is returned
		else if (currNode.r == null){
			return currNode;
		}
		// currNode has a right child and calls itself with right child
		else {
			return leftMax(currNode.r);
		}
	}
    

    public void print(){
        print(root);
    }

    private void print(Node t){
        if (t!=null){
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while(r != null){
                System.out.printf("\t%s\n",r.title);
                r = r.next;
            }
            print(t.r);
        } 
    }
}
