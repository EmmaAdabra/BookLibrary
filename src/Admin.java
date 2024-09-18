public class Admin extends User {
    private String  privilege = "admin";
    public Admin(String name, String email, String password) {
        super(name, email, password);
    }
    RegisterUser registerUser = new RegisterUser();
    @Override
    public void userUI() {
        while (isOnline) {
            System.out.println();
            System.out.println("--------------- Main Menu ---------------");
            System.out.println("1. Add book");
            System.out.println("2. To view all book");
            System.out.println("3. Search for book");
            System.out.println("4. Remove book");
            System.out.println("5. Register User");
            System.out.println("6. Borrow book");
            System.out.println("7. View all book borrowers");
            System.out.println("0. Logout");
            System.out.println();
            int option = IterateInput.intInput( "Option", (byte) 0, (byte)7, validate::validateOption);

            switch (option) {
                case 1 -> library.addBook();
                case 2 -> library.viewAllBook();
                case 3 -> library.searchBook();
                case 4 -> library.removeBook();
                case 5 -> registerUser.getUserDetails();
                case 6 -> library.borrowBook(new Library.Borrower(getUserName(), getUserEmail()));
                case 7 -> library.viewBorrowedBook();
                case 0 -> logout();
            }
        }

    }
}
