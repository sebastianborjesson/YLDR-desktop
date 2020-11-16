import java.sql.*;
import java.util.ArrayList;


public class YLDRStore {

    ArrayList<Order> ord = new ArrayList<>();
    ArrayList<Order> compareOrdersArray = new ArrayList<>();

    public Order[] getOrdrarFromDatabase(String startDate, String endDate, String manufacturer) {
        ord.clear();
        String startdatum = startDate.toString();
        String slutdatum = endDate.toString();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            PreparedStatement ps = conn.prepareStatement("SELECT ordercontent.ordernumber, ordercontent.articlenumber, ordercontent.size, ordercontent.nickname, ordercontent.countryFlag, ordercontent.realName, ordercontent.IsSent, product.manufacturer, purchaseorder.shop, product.productname FROM yldrdb.ordercontent, purchaseorder, product WHERE ordercontent.IsSent!=1 and ordercontent.articlenumber=product.articlenumber and ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ? and product.manufacturer=?;");
            ps.setString(1, startdatum);
            ps.setString(2, slutdatum);
            ps.setString(3,manufacturer);
            ResultSet rs = ps.executeQuery();
            // order=new Order();
            while (rs.next()) {
                Order order=new Order(rs.getString("orderNumber"),rs.getString("articleNumber"),rs.getString("size"),rs.getString("nickname"),rs.getString("countryFlag"),rs.getString("manufacturer"),rs.getBoolean("IsSent"),rs.getString("shop"),rs.getString("productName"),rs.getString("realName"));
                ord.add(order);
                System.out.println("Ordernumber är " +order.getOrderNumber() + "\nArticlenumer är " + order.getArticleNumber() + "\nStorleken är  " + order.getSize() + "\nNickname är " +order.getNickname()+"\nCountryflag är "+order.getCountryFlag() + "\nRealname är " +order.getRealName() +" \nTillverkare är " +order.getTillverkare()+ " \nProductname är "+order.getProductName()+"\nShopen är " +order.getShop());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        Order[] orders = new Order[ord.size()];
        return ord.toArray(orders);
    }

    public void setToIsSent(Order[] orders, String startDate, String endDate, String manufacturer) throws SQLException {
        compareOrdersArray.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            PreparedStatement ps = conn.prepareStatement("Update yldrdb.ordercontent, yldrdb.purchaseorder, yldrdb.product set purchaseorder.date=purchaseorder.date, ordercontent.IsSent=1 where ordercontent.IsSent=0 and ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ? and ordercontent.articlenumber=product.articlenumber and product.manufacturer=?;");
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.setString(3,manufacturer);
            ps.executeUpdate();

            /*PreparedStatement ps2 = conn.prepareStatement("SELECT ordercontent.ordernumber, ordercontent.articlenumber, ordercontent.size, ordercontent.nickname, ordercontent.countryflag, ordercontent.sent, product.manufacturer FROM bDUc0El3cS.ordercontent, purchaseorder, product WHERE sent=1 and ordercontent.articlenumber=product.articlenumber and ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ?;");
            ps2.setString(1, startDate);
            ps2.setString(2, endDate);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                Order order = new Order(rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getBoolean(6), rs2.getString(7));
                compareOrdersArray.add(order);
            }*/
        }

    }
    ArrayList <PurchaseOrder> checkSentArray= new ArrayList<>();
    public PurchaseOrder [] getIsSentorNot() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery("Select purchaseorder.date from yldrdb.purchaseorder, yldrdb.ordercontent where ordercontent.ordernumber=purchaseorder.ordernumber and ordercontent.IsSent=0");
            while (rs.next()){
                PurchaseOrder po= new PurchaseOrder(rs.getTimestamp(1));
                checkSentArray.add(po);
            }
            PurchaseOrder [] array= new PurchaseOrder[checkSentArray.size()];
            /*for (PurchaseOrder o:checkSentArray.toArray(array)) {
                System.out.println(o.getDate());
            }*/
            return checkSentArray.toArray(array);
        }
    }


    ArrayList<PurchaseOrder> pOrd = new ArrayList<>();

    public PurchaseOrder[] getPurchaseInfoFromDatabase(String shop) {
        pOrd.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            // Statement st=conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("Select distinct shop, totalamount from purchaseorder,product, ordercontent where purchaseorder.ordernumber=ordercontent.orderNumber and ordercontent.articleNumber=product.articleNumber and product.manufacturer=?");
            ps.setString(1,shop);
            ResultSet rs = ps.executeQuery();
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            while (rs.next()) {
                purchaseOrder= new PurchaseOrder(rs.getFloat(1),rs.getString(2),rs.getString("shop"));
               // purchaseOrder = new PurchaseOrder(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getTimestamp(5), rs.getString(6));
                pOrd.add(purchaseOrder);
                System.out.println(purchaseOrder.getSumma() + " " + purchaseOrder.getValuta() + " " +purchaseOrder.getShop() );
            }


        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
        PurchaseOrder [] pO=new PurchaseOrder[pOrd.size()];
        return pOrd.toArray(pO);
    }

    public static void getCustomerFromDatabase() {
        ArrayList<Customer> customerArray = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM yldrdb.customer");
            Customer customer = new Customer();
            while (rs.next()) {
                //customer = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getString(7));
                customer=new Customer(rs.getString("email"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("streetAdress"),rs.getString("city"),rs.getString("zipCode"),rs.getString("state"),rs.getString("country"),rs.getString("phoneNumber"));
                customerArray.add(customer);
                System.out.println(customer.getEmail() + " " + customer.getFirstName() + " " + customer.getLastName() + " " + customer.getAdress() + " " + customer.getCity() + " " + customer.getZipCode()+ " " +customer.getPhoneNumber());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
    }

    ArrayList<String> SKU = new ArrayList<>();

    public String[] getDistinctSKU(String startDate, String endDate) {
        SKU.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
           PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT articlenumber FROM yldrdb.ordercontent, purchaseorder where ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ?");
            ps.setString(1, startDate);
            ps.setString(2, endDate);
           ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SKU.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //System.out.println(Arrays.toString(SKU.toArray()));
        String[] articlenumber = new String[SKU.size()];
        return SKU.toArray(articlenumber);
    }


    public void addProduct(String SKU, String productName, String tillverkare) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            PreparedStatement ps = conn.prepareStatement("INSERT into yldrdb.product value (?,?,?)");
            ps.setString(1, SKU);
            ps.setString(2, productName);
            ps.setString(3, tillverkare);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    ArrayList<String> products = new ArrayList<>();

    public String[] getProducts() {
        products.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT articlenumber FROM yldrdb.product");
            while (rs.next()) {
                products.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //System.out.println(Arrays.toString(products.toArray()));
        String[] produkter = new String[products.size()];
        return products.toArray(produkter);
    }

    ArrayList <String>manufacturers=new ArrayList<>();
    public String[] getManufacturer(){
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            Statement st = conn.createStatement();
            ResultSet rs=st.executeQuery("SELECT DISTINCT manufacturer from  yldrdb.product");
            while (rs.next()){
                manufacturers.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] tillverkare=new String[manufacturers.size()];
        return manufacturers.toArray(tillverkare);
    }
    ArrayList<String> shoppar=new ArrayList<>();

    public String[] getDistinctShopFromDatabase(String manufacturer){
        shoppar.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")) {
            PreparedStatement ps = conn.prepareStatement("Select distinct shop from purchaseorder,product, ordercontent where purchaseorder.ordernumber=ordercontent.orderNumber and ordercontent.articleNumber=product.articleNumber and product.manufacturer=? and ordercontent.IsSent!=1");
            ps.setString(1,manufacturer);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                shoppar.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String s:shoppar) {
            System.out.print(s + ", ");
        }
        String[] shoppArray= new String[shoppar.size()];
        return shoppar.toArray(shoppArray);
    }
    public SalesInfo getSalesInfoFromDatabase(String shop){

       // ArrayList<SalesInfo>salesInfos= new ArrayList<>();

        SalesInfo salesInfo=new SalesInfo();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")){
            PreparedStatement ps = conn.prepareStatement("select purchaseorder.totalamount from purchaseorder where purchaseorder.shop=?");
            ps.setString(1,shop);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                salesInfo.setSalesSumm(rs.getFloat("totalamount"));
                salesInfo.setShopName(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")){
            PreparedStatement ps = conn.prepareStatement("SELECT ordercontent.articleNumber, product.productName from purchaseorder,ordercontent,product where purchaseorder.ordernumber=ordercontent.orderNumber and ordercontent.articleNumber=product.articleNumber and purchaseorder.shop=?");
            ps.setString(1,shop);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                salesInfo.setSKUInArray(rs.getString("productName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://yldrdb.yldr.gg:3306/yldrdb", "yldrdb", "IRe4d!QgBVq9fD#jfW")){
            PreparedStatement ps = conn.prepareStatement("select customer.country from customer, purchaseorder where purchaseorder.email=customer.email and purchaseorder.shop=?");
            ps.setString(1,shop);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                salesInfo.setCountryOfSales(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //salesInfos.add(salesInfo);

        //SalesInfo [] salesInfosArray= new SalesInfo[salesInfos.size()];
        return salesInfo;
    }
}
