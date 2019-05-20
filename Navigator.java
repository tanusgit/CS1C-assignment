package stacks;

public class Navigator {
    /*A variable called currentLink of type String.
    */
    private String currentLink;
    /*A variable called backLinks of type StackList<String>.
    */
    private StackList<String> backLinks;
    /*A variable called forwardLinks of type StackList<String>.
     */
    private StackList<String> forwardLinks;

    Navigator(){
        currentLink = "";
        backLinks = new StackList<String>("Back");
        forwardLinks = new StackList<String>("Forward");
    }

    /*A constructor that initializes the class attributes.
     */
    Navigator(String c, StackList<String> b, StackList<String> f){
        currentLink = c;
        backLinks = b;
        forwardLinks = f;
    }
    /*A method called setCurrentLink() which takes an object
    of type String​ for the current requested
    link and updates the backLinks and forwardLinks stacks.*/
    public void setCurrentLink(String link){
        if(!getCurrentLink().isEmpty())
        {
            backLinks.push(getCurrentLink());
        }
        this.currentLink = link;
        forwardLinks.clear();
    }

    /*Two methods called goBack() and goForward() which update the
    currentLink to the link at the top of the backLinks stack
    or forwardLinks stack respectively.*/
    public void goBack(){
        if (getBackLinks().isEmpty())
        {
            return;
        }
        //else
        forwardLinks.push(getCurrentLink());
        this.currentLink = backLinks.peek();
        backLinks.pop();
    }
    public void goForward(){
        if (getForwardLinks().isEmpty())
        {
            return;
        }
        //else
        backLinks.push(getCurrentLink());
        this.currentLink = forwardLinks.peek();
        forwardLinks.pop();
    }

    /* Accessor methods for the class attributes.
    */
    public String getCurrentLink() {
        return this.currentLink;
    }
    /* Accessor methods for the class attributes.
     */
    public StackList getBackLinks() {
        return this.backLinks;
    }
    /* Accessor methods for the class attributes.
     */
    public StackList getForwardLinks() {
        return this.forwardLinks;
    }
}
