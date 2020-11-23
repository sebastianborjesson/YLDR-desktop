import java.sql.*;
import java.util.ArrayList;


public class YLDRStore {

    ArrayList<Order> ord = new ArrayList<>();
    ArrayList<Order> compareOrdersArray = new ArrayList<>();

    public Order[] getOrdrarFromDatabase(String startDate, String endDate, String manufacturer) {
        ord.clear();
        String startdatum = startDate.toString();
        String slutdatum = endDate.toString();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("SELECT ordercontent.ordernumber, ordercontent.articlenumber, ordercontent.size, ordercontent.nickname, ordercontent.countryFlag, ordercontent.realName, ordercontent.IsSent, product.manufacturer, purchaseorder.shop, product.productname, ordercontent.rang, ordercontent.squadNumber, ordercontent.custom1, ordercontent.custom2, ordercontent.custom3 FROM bDUc0El3cS.ordercontent, purchaseorder, product WHERE ordercontent.IsSent!=1 and ordercontent.articlenumber=product.articlenumber and ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ? and product.manufacturer=?;");
            ps.setString(1, startdatum);
            ps.setString(2, slutdatum);
            ps.setString(3, manufacturer);
            ResultSet rs = ps.executeQuery();
            // order=new Order();
            while (rs.next()) {
                Order order = new Order(rs.getString("orderNumber"), rs.getString("articleNumber"), rs.getString("size"), rs.getString("nickname"), rs.getString("countryFlag"), rs.getString("manufacturer"), rs.getBoolean("IsSent"), rs.getString("shop"), rs.getString("productName"), rs.getString("realName"), rs.getString("rang"), rs.getString("squadNumber"),rs.getString("custom1"),rs.getString("custom2"), rs.getString("custom3"));
                ord.add(order);
                System.out.println("Ordernumber är " + order.getOrderNumber() + "\nArticlenumer är " + order.getArticleNumber() + "\nStorleken är  " + order.getSize() + "\nNickname är " + order.getNickname() + "\nCountryflag är " + order.getCountryFlag() + "\nRealname är " + order.getRealName() + " \nTillverkare är " + order.getTillverkare() + " \nProductname är " + order.getProductName() + "\nShopen är " + order.getShop());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        Order[] orders = new Order[ord.size()];
        return ord.toArray(orders);
    }

    public void setToIsSent(Order[] orders, String startDate, String endDate, String manufacturer) throws SQLException {
        compareOrdersArray.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("Update bDUc0El3cS.ordercontent, bDUc0El3cS.purchaseorder, bDUc0El3cS.product set purchaseorder.date=purchaseorder.date, ordercontent.IsSent=1 where ordercontent.IsSent=0 and ordercontent.ordernumber=purchaseorder.ordernumber and purchaseorder.date between ? and ? and ordercontent.articlenumber=product.articlenumber and product.manufacturer=?;");
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.setString(3, manufacturer);
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

    ArrayList<PurchaseOrder> checkSentArray = new ArrayList<>();

    public PurchaseOrder[] getIsSentorNot() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select purchaseorder.date from bDUc0El3cS.purchaseorder, bDUc0El3cS.ordercontent where ordercontent.ordernumber=purchaseorder.ordernumber and ordercontent.IsSent=0");
            while (rs.next()) {
                PurchaseOrder po = new PurchaseOrder(rs.getTimestamp(1));
                checkSentArray.add(po);
            }
            PurchaseOrder[] array = new PurchaseOrder[checkSentArray.size()];
            /*for (PurchaseOrder o:checkSentArray.toArray(array)) {
                System.out.println(o.getDate());
            }*/
            return checkSentArray.toArray(array);
        }
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
        //System.out.println(Arrays.toString(SKU.toArray()));
        String[] articlenumber = new String[SKU.size()];
        return SKU.toArray(articlenumber);
    }


    public void addProduct(String SKU, String productName, String tillverkare, float pris) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("INSERT into bDUc0El3cS.product value (?,?,?,?)");
            ps.setString(1, SKU);
            ps.setString(2, productName);
            ps.setString(3, tillverkare);
            ps.setFloat(4, pris);
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
        //System.out.println(Arrays.toString(products.toArray()));
        String[] produkter = new String[products.size()];
        return products.toArray(produkter);
    }

    ArrayList<String> manufacturers = new ArrayList<>();

    public String[] getManufacturer() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT manufacturer from  bDUc0El3cS.product");
            while (rs.next()) {
                manufacturers.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] tillverkare = new String[manufacturers.size()];
        return manufacturers.toArray(tillverkare);
    }

    ArrayList<String> shoppar = new ArrayList<>();

    public String[] getDistinctShopFromDatabase(String manufacturer) {
        shoppar.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("Select distinct shop from purchaseorder,product, ordercontent where purchaseorder.ordernumber=ordercontent.orderNumber and ordercontent.articleNumber=product.articleNumber and product.manufacturer=? and ordercontent.IsSent!=1");
            ps.setString(1, manufacturer);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                shoppar.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String s : shoppar) {
            System.out.print(s + ", ");
        }
        String[] shoppArray = new String[shoppar.size()];
        return shoppar.toArray(shoppArray);
    }

    public String[] getShopsFromDatabase() {
        shoppar.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select distinct shop from purchaseorder");
            while (rs.next()) {
                shoppar.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] shopArray = new String[shoppar.size()];
        return shoppar.toArray(shopArray);
    }

    public SalesInfo getSalesInfoFromDatabase(String shop, String startDate, String endDate) {

        // ArrayList<SalesInfo>salesInfos= new ArrayList<>();
        SalesInfo salesInfo = new SalesInfo();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("select SUM(purchaseorder.shippingSum) as shippingSum from purchaseorder where purchaseorder.shop=? and purchaseorder.date between ? and ?");
            ps.setString(1, shop);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                salesInfo.setShippingSum(rs.getFloat("shippingSum"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("select purchaseorder.totalamount, purchaseorder.currency from purchaseorder where purchaseorder.shop=? and purchaseorder.date between ? and ?");
            ps.setString(1, shop);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                salesInfo.setSalesSumm(rs.getFloat("totalamount"));
                salesInfo.setValuta(rs.getString("currency"));
                salesInfo.setShopName(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("SELECT customer.country, ordercontent.articleNumber, product.productName, product.price, ordercontent.size from purchaseorder,ordercontent,product, customer where purchaseorder.ordernumber=ordercontent.orderNumber and purchaseorder.email=customer.email and ordercontent.articleNumber=product.articleNumber and purchaseorder.shop=? and purchaseorder.date between ? and ?");
            ps.setString(1, shop);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                salesInfo.setProductInArray(new Product(rs.getString("articlenumber"),rs.getString("productName"), rs.getFloat("price"), rs.getString("country"), rs.getString("size")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("select customer.country from customer, purchaseorder where purchaseorder.email=customer.email and purchaseorder.shop=? and purchaseorder.date between ? and ?");
            ps.setString(1, shop);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                salesInfo.setCountryOfSales(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //salesInfos.add(salesInfo);

        //SalesInfo [] salesInfosArray= new SalesInfo[salesInfos.size()];
        return salesInfo;
    }

    ArrayList<Order> orders = new ArrayList<>();

    public Order[] getRowFromOrdercontent(String ordernumber) throws SQLException {

        orders.clear();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("SELECT * from ordercontent where orderNumber=?");
            ps.setString(1, ordernumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderNumber(rs.getString("orderNumber"));
                order.setArticleNumber(rs.getString("articleNumber"));
                order.setSize(rs.getString("size"));
                order.setNickname(rs.getString("nickname"));
                order.setCountryFlag(rs.getString("countryFlag"));
                order.setRealName(rs.getString("realName"));
                order.setRang(rs.getString("rang"));
                order.setSquadNumber(rs.getString("squadNumber"));
                order.setCustom1(rs.getString("custom1"));
                order.setCustom2(rs.getString("custom2"));
                order.setCustom3(rs.getString("custom3"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Order[] orderArray = new Order[orders.size()];
        return orders.toArray(orderArray);
    }

    public void deleteRowFromOC(String orderNumber, String articleNumber, String size, String nickname, String countryFlag, String realName, String rang, String squadNumber, String custom1, String custom2, String custom3) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("Delete from ordercontent where orderNumber = ? and articleNumber = ? and (size =? or size is null or size ='') and (nickname =? or nickname = '' or nickname is null) and (countryFlag = ? or countryFlag = '' or countryFlag is null) and (realName = ? or realName = '' or realName is null) and (rang = ? or rang = '' or rang is null) and (squadNumber = ? or squadNumber = '' or squadNumber is null) and (custom1 = ? or custom1 = '' or custom1 is null) and (custom2 = ? or custom2 = '' or custom2 is null) and (custom3 = ? or custom3 = '' or custom3 is null) limit 1");
            ps.setString(1, orderNumber);
            ps.setString(2, articleNumber);
            ps.setString(3, size);
            ps.setString(4, nickname);
            ps.setString(5, countryFlag);
            ps.setString(6, realName);
            ps.setString(7,rang);
            ps.setString(8,squadNumber);
            ps.setString(9,custom1);
            ps.setString(10,custom2);
            ps.setString(11,custom3);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*5fa27fead53acb32ae4608d4,rb50505_mythi_2020,S,"","","",0
5fa27fead53acb32ae4608d4,rb50505_mythi_2020_c,M,tobbnen,"","",0
5fa27fead53acb32ae4608d4,rb22603_mythi_2020,M,"","","",0
5fa27fead53acb32ae4608d4,rb22603_mythi_2020_c,L,Vad du vill,"","",0
5fa27fead53acb32ae4608d4,rb54004_mythi_2020,XS,"","","",0
*/
    }

    public void deleteFromPO(String orderNumber) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("Delete from purchaseorder where ordernumber = ?");
            ps.setString(1, orderNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrderCalculator getPricingInfo(String ordernumber, String articlenumber, String size, String nickname, String realName, String countryFlag, String rang, String squadNumber, String custom1, String custom2, String custom3) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("Select purchaseorder.totalamount, purchaseorder.tax, purchaseorder.shippingSum, purchaseorder.discountPercentage, product.productName, product.price from purchaseorder, product, ordercontent where purchaseorder.orderNumber= ordercontent.orderNumber and ordercontent.articleNumber=product.articleNumber and ordercontent.orderNumber = ? and ordercontent.articleNumber = ? and (ordercontent.size = ? or ordercontent.size is null) and (ordercontent.nickname = ? or ordercontent.nickname is null) and (ordercontent.realName = ? or ordercontent.realName is null) and (ordercontent.countryFlag = ? or ordercontent.countryFlag is null) and (rang = ? or rang = '' or rang is null) and (squadNumber = ? or squadNumber = '' or squadNumber is null) and (custom1 = ? or custom1 = '' or custom1 is null) and (custom2 = ? or custom2 = '' or custom2 is null) and (custom3 = ? or custom3 = '' or custom3 is null)");
            ps.setString(1, ordernumber);
            ps.setString(2, articlenumber);
            ps.setString(3, size);
            ps.setString(4, nickname);
            ps.setString(5, realName);
            ps.setString(6, countryFlag);
            ps.setString(7,rang);
            ps.setString(8,squadNumber);
            ps.setString(9,custom1);
            ps.setString(10,custom2);
            ps.setString(11,custom3);

            OrderCalculator oc = new OrderCalculator();
            Product product = new Product();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                oc.setSumma(rs.getFloat("totalamount"));
                oc.setRabatt(rs.getFloat("discountPercentage"));
                oc.setTax(rs.getFloat("tax"));
                oc.setShippingCost(rs.getFloat("shippingSum"));
                product.setPrice(rs.getFloat("price"));
                product.setName(rs.getString("productName"));
                oc.setProductInfo(product);

            }
            return oc;

        }
    }

    public void updatePurchaseOrderInfo(float nyaTotalSumman, float nyaSkatteSumman, String ordernumber) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("UPDATE purchaseorder set purchaseorder.totalamount=?, purchaseorder.tax=? where purchaseorder.orderNumber=?;");
            ps.setFloat(1, nyaTotalSumman);
            ps.setFloat(2, nyaSkatteSumman);
            ps.setString(3, ordernumber);
            ps.executeUpdate();
        }


    }

    ArrayList<PurchaseOrder> pOrd = new ArrayList<>();

    public PurchaseOrder[] getPurchaseInfoFromDatabase() {
        pOrd.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            // Statement st=conn.createStatement();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT  * from purchaseorder");
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            while (rs.next()) {
                purchaseOrder = new PurchaseOrder(rs.getString("ordernumber"), rs.getString("email"), rs.getFloat("totalamount"), rs.getString("currency"), rs.getDate("date"), rs.getString("shop"), rs.getBoolean("isEmailSent"), rs.getFloat("discountPercentage"), rs.getFloat("tax"), rs.getFloat("shippingSum"));
                // purchaseOrder = new PurchaseOrder(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getTimestamp(5), rs.getString(6));
                pOrd.add(purchaseOrder);
                System.out.println(purchaseOrder.getOrderNumber() + "||" + purchaseOrder.getEmail() + "||" + purchaseOrder.getSumma() + "||" + purchaseOrder.getValuta() + "||" + purchaseOrder.getDate().toString() + "||" + purchaseOrder.getShop() + "||" + purchaseOrder.getIsEmailSent() + "||" + purchaseOrder.getDiscountPercentage() + "||" + purchaseOrder.getTax() + "||" + purchaseOrder.getShippingSum());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();
        PurchaseOrder[] pO = new PurchaseOrder[pOrd.size()];
        return pOrd.toArray(pO);
    }

    ArrayList<Customer> customerArray = new ArrayList<>();

    public Customer[] getCustomerFromDatabase() {
        customerArray.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bDUc0El3cS.customer");
            Customer customer = new Customer();
            while (rs.next()) {
                //customer = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),rs.getString(7));
                customer = new Customer(rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("streetAdress"), rs.getString("city"), rs.getString("zipCode"), rs.getString("state"), rs.getString("country"), rs.getString("phoneNumber"));
                customerArray.add(customer);
                System.out.println(customer.getEmail() + "||" + customer.getFirstName() + "||" + customer.getLastName() + "||" + customer.getAdress() + "||" + customer.getCity() + "||" + customer.getZipCode() + "||" + customer.getState() + "||" + customer.getCountry() + "||" + customer.getPhoneNumber());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
        Customer[] customers = new Customer[customerArray.size()];
        return customerArray.toArray(customers);
    }

    /*  public Order [] getEverythingFromOrdercontent() throws SQLException {
          try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
              Statement st = conn.createStatement();
              ResultSet rs= st.executeQuery("SELECT * from ordercontent");
              Order order= new Order();
              while(rs.next()){

              }
          }
      }*/
    ArrayList<Product> productArrayList = new ArrayList<>();

    public Product[] getEverythingFromProduct() throws SQLException {
        productArrayList.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            //PreparedStatement ps= conn.prepareStatement("SELECT * from product");
            ResultSet rs = st.executeQuery("SELECT * from product");

            while (rs.next()) {
                Product product = new Product();
                //System.out.println(rs.getString("articlenumber") + rs.getString("productname") + rs.getString("manufacturer") + rs.getFloat("price"));
                product.setSKU(rs.getString("articlenumber"));
                product.setName(rs.getString("productname"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setPrice(rs.getFloat("price"));
                productArrayList.add(product);
                System.out.println(product.getSKU() + " || " + product.getName() + " || " + product.getManufacturer() + " || " + product.getPrice());
            }
            Product[] products = new Product[productArrayList.size()];
            return productArrayList.toArray(products);
        }
    }

    public void updateProduct(String newValue, String articleNumber, String column) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("UPDATE product set " + column + "= ? where articleNumber = ?");
            if (column.equals("price")) {
                float f = Float.parseFloat(newValue);
                ps.setFloat(1, f);
                ps.setString(2, articleNumber);
            } else {
                ps.setString(1, newValue);
                ps.setString(2, articleNumber);
            }
            ps.executeUpdate();
        }
    }

    public void deleteTestOrderFromOC(String orderNumber) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("DELETE from ordercontent where orderNumber = ?");
            ps.setString(1, orderNumber);
            ps.executeUpdate();
        }
    }
    ArrayList<LogistikInfo> logistikInfoArrayList= new ArrayList<>();

    public LogistikInfo [] getLogisticInfo(String startDate, String endDate) throws SQLException {
        logistikInfoArrayList.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps= conn.prepareStatement("SELECT ordercontent.articleNumber, product.productName, ordercontent.orderNumber, CONCAT(customer.firstName, ' ' ,customer.lastName) as Name, customer.streetAdress, customer.zipCode, customer.city, customer.phoneNumber, customer.email, customer.country, ordercontent.nickname, ordercontent.size from customer, product, purchaseorder, ordercontent where customer.email = purchaseorder.email and purchaseorder.orderNumber = ordercontent.orderNumber and product.articleNumber = ordercontent.articleNumber and ordercontent.isSentToLogistics=0 and purchaseorder.date between ? and ?");
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LogistikInfo logistikInfo = new LogistikInfo();
                logistikInfo.setArticlenumber(rs.getString("articleNumber"));
                logistikInfo.setArticleName(rs.getString("productName"));
                logistikInfo.setCustomerOrdernumber(rs.getString("orderNumber"));
                logistikInfo.setCustomerName(rs.getString("Name"));
                logistikInfo.setCustomerAdress(rs.getString("streetAdress"));
                logistikInfo.setCustomerPostCode(rs.getString("zipCode"));
                logistikInfo.setCustomerCity(rs.getString("city"));
                logistikInfo.setCustomerPhoneNumber(rs.getString("phoneNumber"));
                logistikInfo.setCustomerEmail(rs.getString("email"));
                logistikInfo.setCountry(rs.getString("country"));
                logistikInfo.setNickname(rs.getString("nickname"));
                logistikInfo.setSize(rs.getString("size"));
                setIsSentToLogistics(logistikInfo.getCustomerOrdernumber());
                System.out.println(logistikInfo.getArticlenumber());

                logistikInfoArrayList.add(logistikInfo);
            }
            LogistikInfo[] logistikInfoArray = new LogistikInfo[logistikInfoArrayList.size()];
            return logistikInfoArrayList.toArray(logistikInfoArray);
        }

    }

    public void setIsSentToLogistics(String ordernumber) throws SQLException {
        compareOrdersArray.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("Update bDUc0El3cS.ordercontent set ordercontent.isSentToLogistics=1 where ordercontent.ordernumber=?;");
            ps.setString(1, ordernumber);
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

    ArrayList<Order> orderArrayList= new ArrayList<>();
    public Order [] getOC() throws SQLException {
        orderArrayList.clear();
        try(Connection conn= DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            Statement st = conn.createStatement();
            ResultSet rs= st.executeQuery("SELECT * from ordercontent");
            while(rs.next()){
                Order order=new Order();
                order.setOrderNumber(rs.getString("orderNumber"));
                order.setArticleNumber(rs.getString("articleNumber"));
                order.setSize(rs.getString("size"));
                order.setNickname(rs.getString("nickname"));
                order.setCountryFlag(rs.getString("countryFlag"));
                order.setRealName(rs.getString("realName"));
                order.setRang(rs.getString("rang"));
                order.setSquadNumber(rs.getString("squadNumber"));
                order.setCustom1(rs.getString("Custom1"));
                order.setCustom2(rs.getString("Custom2"));
                order.setCustom3(rs.getString("Custom3"));
                order.setIsSent(rs.getBoolean("IsSent"));
                order.setLogisticsSent(rs.getBoolean("isSentToLogistics"));
                orderArrayList.add(order);
            }
            Order [] orders= new Order[orderArrayList.size()];
            return orderArrayList.toArray(orders);
        }
    }

    public void updateISSenttoLogistics(boolean newValue, String column, String ordernumber, String articlenumber, String size, String nickname, String realName, String countryFlag, String rang, String squadNumber, String custom1, String custom2, String custom3) {
        try(Connection conn= DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps =conn.prepareStatement("Update ordercontent set " + column +"=? where ordercontent.orderNumber = ? and ordercontent.articleNumber = ? and (ordercontent.size = ? or ordercontent.size is null) and (ordercontent.nickname = ? or ordercontent.nickname is null) and (ordercontent.realName = ? or ordercontent.realName is null) and (ordercontent.countryFlag = ? or ordercontent.countryFlag is null) and (rang = ? or rang = '' or rang is null) and (squadNumber = ? or squadNumber = '' or squadNumber is null) and (custom1 = ? or custom1 = '' or custom1 is null) and (custom2 = ? or custom2 = '' or custom2 is null) and (custom3 = ? or custom3 = '' or custom3 is null)");
            ps.setBoolean(1, newValue);
            ps.setString(2, ordernumber);
            ps.setString(3, articlenumber);
            ps.setString(4, size);
            ps.setString(5, nickname);
            ps.setString(6, realName);
            ps.setString(7, countryFlag);
            ps.setString(8, rang);
            ps.setString(9, squadNumber);
            ps.setString(10,custom1);
            ps.setString(11,custom2);
            ps.setString(12,custom3);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*public void updateOrderContent(boolean newValue, String orderNumber) {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/bDUc0El3cS?useSSL=false", "bDUc0El3cS", "zvIgPALCPI")) {
            PreparedStatement ps = conn.prepareStatement("UPDATE ordercontent set isSent")
        }
    }*/
}