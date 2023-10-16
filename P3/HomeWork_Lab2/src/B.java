// Class B: Class B has two members, an instance of class A (memberA) and an array of class C objects (memberCArray)
// The constructor of class B initializes these members
public class B {
    private A memberA;
    private C[] memberCArray;

    public B(A memberA, C[] memberCArray) {
        this.memberA = memberA;
        this.memberCArray = memberCArray;
    }

    public A getMemberA() {
        return memberA;
    }

    public C[] getMemberCArray() {
        return memberCArray;
    }
}
