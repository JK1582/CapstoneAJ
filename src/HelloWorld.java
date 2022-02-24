	/* 
		/ open a connection
      try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();) {
             //System.out.println(AddCompany());

            // --CREATE TABLE TO HOLD COMPANY EMPLOYEE INFO--
                    //this is going to act as like "logging in" for the company... lets remember that for the GUI 
            Scanner myObj = new Scanner(System.in);
            String company_name;
            System.out.println("Enter company name: "); // user input of employee name
            company_name = myObj.nextLine();
            System.out.println(company_name + "_employees");

            // TODO: add error checking logic to see if table already exists etc.

           //TODO: add error checking logic to see if table already exists etc. 
			String tblName = company_name + "_employees";
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, tblName, null);
			if(tables.next()) {
				//if table exists
				System.out.println("Company already registered");
                System.out.println("Adding Employees");
                System.out.println("Enter id: ");
				String empId = myObj.nextLine();
				System.out.println("Enter first name: ");
				String firstName = myObj.nextLine();
				System.out.println("Enter last name: ");
				String lastName = myObj.nextLine();
				System.out.println("Enter email: ");
				String empEmail = myObj.nextLine();
				

			      // the mysql insert statement
			      String query = " insert into " + company_name + "_employees  (id, fname, lname, email)"
			        + " values (?, ?, ?, ?)";

			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      preparedStmt.setString (1, empId);
			      preparedStmt.setString (2, firstName);
			      preparedStmt.setString   (3, lastName);
			      preparedStmt.setString    (4, empEmail);
			      preparedStmt.execute();
				
				System.out.println("Data inserted.");
				
				//TODO: give them options to add employee, update employee info, remove employee
			}
			else {
				//table does not exist
				System.out.println("Register company");
				String sql_table = "CREATE TABLE " + company_name + "_employees " +
		                   "(id INTEGER not NULL, " +
		                   " fname VARCHAR(255), " + 
		                   " lname VARCHAR(255), " + 
		                   " email VARCHAR(255), " + 
		                   " PRIMARY KEY ( id ))"; 
				
				stmt.executeUpdate(sql_table);
				System.out.println("Created table in database");
				
				//TODO: Joe add in code to give option to add more employees
				//if you can, add code so can upload excel or csv file!
				
				//--ADD IN EMPLOYEE DATA TO TABLE--
				
			}
            
            
			
        } 
		catch (SQLException e) {
          e.printStackTrace();
        }
		*/
        