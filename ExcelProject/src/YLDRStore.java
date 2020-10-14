import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


public class YLDRStore {

    ArrayList<Order> ord = new ArrayList<>();
    ArrayList<Order> compareOrdersArray = new ArrayList<>();

    public Order[] getOrdrarFromDatabase(String startDate, String endDate) {
        ord.clear();
        String startdatum = startDate.toString();
        String slutdatum = endDate.toString();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("SELECT ordercontent.ordernumber, ordercontent.articlenumber, ordercontent.size, ordercontent.nickname, ordercontent.countryflag, ordercontent.sent, product.manufacturer FROM bDUc0El3cS.ordercontent, purchaseorder, product WHERE sent!=1 and ordercontent.articlenumber=product.articlenumber and ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ?;");
            ps.setString(1, startdatum);
            ps.setString(2, slutdatum);
            ResultSet rs = ps.executeQuery();
            // order=new Order();
            while (rs.next()) {
                Order order = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getString(7));
                ord.add(order);
                //System.out.println(order.getOrderNumber() + " " + order.getArticleNumber() + " " + order.getSize() + " " + order.getTillverkare());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        Order[] orders = new Order[ord.size()];
        return ord.toArray(orders);
    }

    public void setToIsSent(Order[] orders, String startDate, String endDate) throws SQLException {
        compareOrdersArray.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("Update bDUc0El3cS.ordercontent, bDUc0El3cS.purchaseorder set sent=1, purchaseorder.date=purchaseorder.date, IsSent=1 where sent=0 and IsSent=0 and ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ?;");
            ps.setString(1, startDate);
            ps.setString(2, endDate);
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
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery("Select purchaseorder.date from bDUc0El3cS.purchaseorder where IsSent=0");
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

    public void getPurchaseInfoFromDatabase() {
        pOrd.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bDUc0El3cS.purchaseorder");
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            while (rs.next()) {
                purchaseOrder = new PurchaseOrder(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getTimestamp(5), rs.getString(6));
                pOrd.add(purchaseOrder);
                //System.out.println(purchaseOrder.getOrderNumber() + " " + purchaseOrder.getEmail()+ " " +purchaseOrder.getSumma()+ " " + purchaseOrder.getValuta()+" " +  purchaseOrder.getShop()+" "+ purchaseOrder.getDate());
            }


        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
    }

    public static void getCustomerFromDatabase() {
        ArrayList<Customer> customerArray = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bDUc0El3cS.customer");
            Customer customer = new Customer();
            while (rs.next()) {
                customer = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
                customerArray.add(customer);
                System.out.println(customer.getEmail() + " " + customer.getFirstName() + " " + customer.getLastName() + " " + customer.getAdress() + " " + customer.getCity() + " " + customer.getZipCode());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
    }

    ArrayList<String> SKU = new ArrayList<>();

    public String[] getDistinctSKU(String startDate, String endDate) {
        SKU.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
           PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT articlenumber FROM bDUc0El3cS.ordercontent, purchaseorder where ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ?");
            ps.setString(1, startDate);
            ps.setString(2, endDate);
           ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SKU.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(Arrays.toString(SKU.toArray()));
        String[] articlenumber = new String[SKU.size()];
        return SKU.toArray(articlenumber);
    }


    public void addProduct(String SKU, String productName, String tillverkare) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("INSERT into bDUc0El3cS.product value (?,?,?)");
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
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT articlenumber FROM bDUc0El3cS.product");
            while (rs.next()) {
                products.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(Arrays.toString(products.toArray()));
        String[] produkter = new String[products.size()];
        return products.toArray(produkter);
    }
}
