/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package libratech.dashboard;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import libratech.auth.signup;
import libratech.books.inshelf.Book;
import libratech.books.inshelf.EventAction;
import libratech.books.inshelf.StatusType;
import libratech.books.inshelf.TableStatus;
import libratech.design.GlassPanePopup;
import libratech.design.ImageScaler;
import libratech.design.RoundedPanel;
import libratech.design.loading;
import libratech.models.ClassificationInfo;
import libratech.models.Dashboard.retBooks;
import libratech.models.getUID;
import libratech.models.pushValue;
import libratech.models.retrieve;
import libratech.util.firebaseInit;
import libratech.util.storage;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

/**
 *
 * @author HBUSER
 */
public class edit_book extends javax.swing.JPanel {

    private String localFilePath;
    private String remoteFilePath;
    private DatabaseReference databaseReference;
    private HashMap<String, Object> m;
    private pushValue v;
    private retrieve r;
    private String uid;
    private String path = "books/" + new getUID().getUid() + "/";
    private DatabaseReference books = FirebaseDatabase.getInstance().getReference(path);
    private DatabaseReference books2;
    private DatabaseReference dbRef;
    private String ck;
    private String title;
    private retBooks listener;
    private ChildEventListener booksinfo;
    private DatabaseReference.CompletionListener completionListener;
    private DefaultTableModel model;
    private libratech.books.inshelf.InshelfTable inshelfTable1;
    String downloadUrl = "";
    String remaining_copies = "";
    boolean upload = false;

    private String path1 = "analytics/" + new getUID().getUid() + "/";
    private DatabaseReference analytics1 = FirebaseDatabase.getInstance().getReference(path);
    private HashMap<String, Object> m1;
    private pushValue t;
    ClassificationInfo info = new ClassificationInfo();
    ImageScaler scaler = new ImageScaler();

    public edit_book(String key1, libratech.books.inshelf.InshelfTable inshelfTable1) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        initComponents();
        initFont();
        new firebaseInit().initFirebase();
        this.listener = new retBooks(key1);
        this.ck = key1;
        System.out.println(key1);
        this.model = (DefaultTableModel) inshelfTable1.getModel();
        this.inshelfTable1 = inshelfTable1;
        this.books2 = FirebaseDatabase.getInstance().getReference(path + ck);
        initFont();
        System.out.println(ck);
        retrieveData();

        classification.setEditable(true);
        classification.setModel(new javax.swing.DefaultComboBoxModel(info.getClassification()));
        classification.getEditor().getEditorComponent().setBackground(new Color(250, 250, 250));

        booksinfo = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = dataSnapshot.getKey();
                final HashMap<String, Object> _childValue = dataSnapshot.getValue(_ind);
                if (_childKey.equals(ck)) {
                    try {
                        booktitle.setText(_childValue.get("booktitle").toString());
                        author.setText(_childValue.get("bookauthor").toString());
                        publisher.setText(_childValue.get("publisher").toString());
                        classification.setSelectedIndex(Integer.parseInt(_childValue.get("classification_pos").toString()));
                        date.setText(_childValue.get("date").toString());
                        copies.setText(_childValue.get("copies").toString());
                        barcode(_childValue.get("barcode").toString());
                        isbn.setText(_childValue.get("isbn").toString());
                        shelf.setText(_childValue.get("shelf").toString());
                        deck.setText(_childValue.get("deck").toString());
                        edition.setText(_childValue.get("edition").toString());
                        URL url = new URL(_childValue.get("cover").toString());
                        BufferedImage image = ImageIO.read(url);
                        photoCover1.setImage(image);
                        downloadUrl = _childValue.get("cover").toString();
                        remaining_copies = _childValue.get("remaining_copies").toString();
                        title = _childValue.get("booktitle").toString();
                        jLabel2.setText("Edit book - " + _childValue.get("remaining_copies").toString() + " of " + _childValue.get("copies").toString() + " copies");
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(edit_book.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(edit_book.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                final String _childKey = ds.getKey();
                final HashMap<String, Object> _childValue = ds.getValue(_ind);
                if (_childKey.equals(ck)) {
                    try {
                        booktitle.setText(_childValue.get("booktitle").toString());
                        author.setText(_childValue.get("bookauthor").toString());
                        publisher.setText(_childValue.get("publisher").toString());
                        classification.setSelectedIndex(Integer.parseInt(_childValue.get("classification_pos").toString()));
                        date.setText(_childValue.get("date").toString());
                        copies.setText(_childValue.get("copies").toString());
                        barcode(_childValue.get("barcode").toString());
                        isbn.setText(_childValue.get("isbn").toString());
                        shelf.setText(_childValue.get("shelf").toString());
                        deck.setText(_childValue.get("deck").toString());
                        edition.setText(_childValue.get("edition").toString());
                        URL url = new URL(_childValue.get("cover").toString());
                        BufferedImage image = ImageIO.read(url);
                        photoCover1.setImage(image);
                        downloadUrl = _childValue.get("cover").toString();
                        remaining_copies = _childValue.get("remaining_copies").toString();
                        title = _childValue.get("booktitle").toString();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(edit_book.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(edit_book.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
        books.addChildEventListener(booksinfo);

        completionListener = (DatabaseError error, DatabaseReference ref) -> {
            if (error != null) {
                System.out.println("Error removing value: " + error.getMessage());
            } else {
                System.out.println("Value removed successfully.");
            }
        };

    }

    private void barcode(String code) {
        try {
            Barcode barcode = BarcodeFactory.createCode128(code);
            barcode.setFont(new Font("Poppins Regular", Font.BOLD, 12));
            barcode.setBarHeight(60);
            barcode.setBarWidth(2);

            // Create a temporary file to save the barcode image
            File file = File.createTempFile("barcode", ".png");
            BarcodeImageHandler.savePNG(barcode, file);
            scaler.scaleImage(jLabel3, file.getAbsolutePath());

        } catch (BarcodeException | OutputException | IOException ex) {
            Logger.getLogger(books_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void retrieveData() {
        // Fetch data from Firebase and create table
        EventAction eventAction = new EventAction() {
            @Override
            public void update(Book book) {
                GlassPanePopup.showPopup(new edit_book(book.getChildKey(), inshelfTable1));
            }
        };

        dbRef = FirebaseDatabase.getInstance().getReference("books/" + new getUID().getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                model.setRowCount(0);

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if ("Available".equals(child.child("status").getValue(String.class))) {
                        String key = child.child("key").getValue(String.class);
                        String bookCoverUrl = child.child("cover").getValue(String.class);
                        String bookTitle = child.child("booktitle").getValue(String.class);
                        String publisher = child.child("publisher").getValue(String.class);
                        String barcode = child.child("barcode").getValue(String.class);
                        String classification = child.child("classification").getValue(String.class);
                        String author = child.child("bookauthor").getValue(String.class);
                        String dewey = child.child("isbn").getValue(String.class);
                        String call_no = child.child("call_number").getValue(String.class);
                        String copies = child.child("copies").getValue(String.class);
                        String deck = child.child("deck").getValue(String.class);
                        String shelf = child.child("shelf").getValue(String.class);
                        String date = child.child("date").getValue(String.class);
                        String status = child.child("status").getValue(String.class);

//                        if (Integer.parseInt(child.child("remaining_copies").getValue(String.class)) <= 0) {
//                            String getnow = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
//                            String uidpath = new getUID().getUid();
//                            v = new pushValue(key);
//                            m = new HashMap<>();
//                            m.put("booktitle", bookTitle);
//                            m.put("bookauthor", author);
//                            m.put("publisher", publisher);
//                            m.put("isbn", child.child("isbn").getValue(String.class));
//                            m.put("classification_code", child.child("classification_code").getValue(String.class));
//                            m.put("classification_pos", child.child("classification_pos").getValue(String.class));
//                            m.put("classification", child.child("classification").getValue(String.class));
//                            m.put("date", child.child("date").getValue(String.class));
//                            m.put("copies", child.child("copies").getValue(String.class));
//                            m.put("edition", child.child("edition").getValue(String.class));
//                            m.put("shelf", child.child("shelf").getValue(String.class));
//                            m.put("deck", child.child("deck").getValue(String.class));
//                            m.put("key", key);
//                            m.put("call_number", call_no);
//                            m.put("status", "Borwwwrowed");
//                            m.put("remaining_copies", child.child("remaining_copies").getValue(String.class));
//                            m.put("timestamp", getnow);
//                            m.put("cover", bookCoverUrl);
//                            v.pushData("books/" + uidpath, m);
//                            m.clear();
//                        }
                        TableStatus statust = new TableStatus();

                        if (status.equals("Available")) {
                            statust.setType(StatusType.Available);
                        } else if (status.equals("Borrowed")) {
                            statust.setType(StatusType.Borrowed);
                        } else if (status.equals("Lost")) {
                            statust.setType(StatusType.Lost);
                        } else if (status.equals("Damaged")) {
                            statust.setType(StatusType.Damaged);
                        } else {
                            statust.setType(StatusType.Returned);
                        }
                        inshelfTable1.addRow(new Book(bookTitle, publisher, classification, author, barcode, copies, statust.getType(), key).toRowTable(eventAction));
                        new Book().setChildKey(key);
                        model.fireTableDataChanged();
                        inshelfTable1.repaint();
                        inshelfTable1.revalidate();
                    }

                }

                t = new pushValue("inshelf");
                m1 = new HashMap<>();
                m1.put("total", model.getRowCount());
                t.pushData(path1, m1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        g2.dispose();
        super.paintComponent(graphics);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        coverlabel = new javax.swing.JLabel();
        allowedtype = new javax.swing.JLabel();
        booktitlelabel = new javax.swing.JLabel();
        jPanel6 = new RoundedPanel(12, new Color(250,250,250));
        booktitle = new javax.swing.JTextField();
        photoCover1 = new libratech.design.PhotoCover();
        authorlabel = new javax.swing.JLabel();
        jPanel7 = new RoundedPanel(12, new Color(250,250,250));
        author = new javax.swing.JTextField();
        jPanel8 = new RoundedPanel(12, new Color(250,250,250));
        publisher = new javax.swing.JTextField();
        publisherlabel = new javax.swing.JLabel();
        classificationlabel = new javax.swing.JLabel();
        jPanel9 = new RoundedPanel(12, new Color(250,250,250));
        classification = new libratech.design.ComboBoxSuggestion();
        jPanel10 = new RoundedPanel(12, new Color(250,250,250));
        isbn = new javax.swing.JTextField();
        isbnlabel = new javax.swing.JLabel();
        datelabel = new javax.swing.JLabel();
        jPanel11 = new RoundedPanel(12, new Color(250,250,250));
        date = new javax.swing.JTextField();
        copieslabel = new javax.swing.JLabel();
        jPanel12 = new RoundedPanel(12, new Color(250,250,250));
        copies = new javax.swing.JTextField();
        decklabel = new javax.swing.JLabel();
        jPanel13 = new RoundedPanel(12, new Color(250,250,250));
        deck = new javax.swing.JTextField();
        shelflabel = new javax.swing.JLabel();
        jPanel14 = new RoundedPanel(12, new Color(250,250,250));
        shelf = new javax.swing.JTextField();
        myButtonborderless2 = new libratech.design.MyButtonborderless();
        myButtonborder1 = new libratech.design.MyButtonborder();
        jPanel15 = new RoundedPanel(12, new Color(250,250,250));
        edition = new javax.swing.JTextField();
        editionlabel = new javax.swing.JLabel();
        delete = new libratech.design.MyButtonborder();
        myButtonborderless3 = new libratech.design.MyButtonborderless();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(250, 250, 250));
        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(58, 58, 58));
        jLabel2.setText("Edit book");

        coverlabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        coverlabel.setForeground(new java.awt.Color(51, 51, 51));
        coverlabel.setText("Cover");

        allowedtype.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        allowedtype.setForeground(new java.awt.Color(158, 158, 158));
        allowedtype.setText("Allowed file types: png, jpeg, or jpg");

        booktitlelabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        booktitlelabel.setForeground(new java.awt.Color(51, 51, 51));
        booktitlelabel.setText("Book Title");

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setOpaque(false);

        booktitle.setBackground(new java.awt.Color(250, 250, 250));
        booktitle.setBorder(null);
        booktitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booktitleActionPerformed(evt);
            }
        });
        booktitle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                booktitleKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(booktitle, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(booktitle, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        photoCover1.setBackground(new java.awt.Color(158, 158, 158));
        photoCover1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        photoCover1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                photoCover1MouseClicked(evt);
            }
        });

        authorlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        authorlabel.setForeground(new java.awt.Color(51, 51, 51));
        authorlabel.setText("Author");

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setOpaque(false);

        author.setBackground(new java.awt.Color(250, 250, 250));
        author.setBorder(null);
        author.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorActionPerformed(evt);
            }
        });
        author.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                authorKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(author, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(316, 316, 316))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(author, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));
        jPanel8.setOpaque(false);

        publisher.setBackground(new java.awt.Color(250, 250, 250));
        publisher.setBorder(null);
        publisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publisherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(publisher, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(316, 316, 316))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(publisher, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        publisherlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        publisherlabel.setForeground(new java.awt.Color(51, 51, 51));
        publisherlabel.setText("Publisher");

        classificationlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        classificationlabel.setForeground(new java.awt.Color(51, 51, 51));
        classificationlabel.setText("Classification");

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));
        jPanel9.setOpaque(false);

        classification.setBackground(new java.awt.Color(255, 255, 255));
        classification.setEditable(false);
        classification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classificationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(classification, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(classification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setOpaque(false);

        isbn.setBackground(new java.awt.Color(250, 250, 250));
        isbn.setBorder(null);
        isbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isbnActionPerformed(evt);
            }
        });
        isbn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                isbnKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(isbn, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(598, 598, 598))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(isbn, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        isbnlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        isbnlabel.setForeground(new java.awt.Color(51, 51, 51));
        isbnlabel.setText("ISBN Number");

        datelabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        datelabel.setForeground(new java.awt.Color(51, 51, 51));
        datelabel.setText("Copyright Year");

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setOpaque(false);

        date.setBackground(new java.awt.Color(250, 250, 250));
        date.setBorder(null);
        date.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateMouseClicked(evt);
            }
        });
        date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(554, 554, 554))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(date)
                .addContainerGap())
        );

        copieslabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        copieslabel.setForeground(new java.awt.Color(51, 51, 51));
        copieslabel.setText("Number of Copies");

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.setOpaque(false);

        copies.setBackground(new java.awt.Color(250, 250, 250));
        copies.setBorder(null);
        copies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiesActionPerformed(evt);
            }
        });
        copies.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                copiesKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(copies, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(566, 566, 566))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(copies, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        decklabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        decklabel.setForeground(new java.awt.Color(51, 51, 51));
        decklabel.setText("Deck Number");

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setOpaque(false);

        deck.setBackground(new java.awt.Color(250, 250, 250));
        deck.setBorder(null);
        deck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deckActionPerformed(evt);
            }
        });
        deck.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                deckKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deck, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(566, 566, 566))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(deck, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        shelflabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        shelflabel.setForeground(new java.awt.Color(51, 51, 51));
        shelflabel.setText("Shelf Number");

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setOpaque(false);

        shelf.setBackground(new java.awt.Color(250, 250, 250));
        shelf.setBorder(null);
        shelf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shelfActionPerformed(evt);
            }
        });
        shelf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                shelfKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(shelf, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(569, 569, 569))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(shelf, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        myButtonborderless2.setForeground(new java.awt.Color(224, 224, 224));
        myButtonborderless2.setText("Save changes");
        myButtonborderless2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButtonborderless2ActionPerformed(evt);
            }
        });

        myButtonborder1.setForeground(new java.awt.Color(23, 23, 23));
        myButtonborder1.setText("Cancel");
        myButtonborder1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButtonborder1ActionPerformed(evt);
            }
        });

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));
        jPanel15.setOpaque(false);

        edition.setBackground(new java.awt.Color(250, 250, 250));
        edition.setBorder(null);
        edition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editionActionPerformed(evt);
            }
        });
        edition.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                editionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(edition, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(546, 546, 546))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(edition, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        editionlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        editionlabel.setForeground(new java.awt.Color(51, 51, 51));
        editionlabel.setText("Book Edition");

        delete.setForeground(new java.awt.Color(23, 23, 23));
        delete.setText("Delete");
        delete.setPreferredSize(new java.awt.Dimension(102, 23));
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        myButtonborderless3.setForeground(new java.awt.Color(224, 224, 224));
        myButtonborderless3.setText("Borrow book");
        myButtonborderless3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButtonborderless3ActionPerformed(evt);
            }
        });

        jLabel3.setPreferredSize(new java.awt.Dimension(433, 52));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(coverlabel)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(classificationlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 276, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(copieslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 133, Short.MAX_VALUE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(allowedtype)
                                .addComponent(booktitlelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(644, 644, 644))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(photoCover1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(isbnlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(14, 14, 14)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(shelflabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(decklabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE))
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 278, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(myButtonborder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(myButtonborderless2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(publisherlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(authorlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(datelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(editionlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                        .addGap(10, 10, 10)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(myButtonborderless3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(myButtonborderless3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coverlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(photoCover1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allowedtype)
                .addGap(18, 18, 18)
                .addComponent(booktitlelabel)
                .addGap(4, 4, 4)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(authorlabel)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editionlabel)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(publisherlabel)
                                .addGap(4, 4, 4)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(datelabel)
                                .addGap(4, 4, 4)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(classificationlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(copieslabel)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(decklabel)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(isbnlabel)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(shelflabel)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButtonborderless2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButtonborder1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void booktitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booktitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_booktitleActionPerformed

    private void authorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorActionPerformed

    private void publisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publisherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_publisherActionPerformed

    private void isbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isbnActionPerformed

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void copiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_copiesActionPerformed

    private void shelfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shelfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shelfActionPerformed

    private void myButtonborderless2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButtonborderless2ActionPerformed
        // TODO add your handling code here:
        String book_title = booktitle.getText();
        String book_author = author.getText();
        String publ = publisher.getText();
        String book_isbn = isbn.getText();
        String genr = info.getDeweyNo()[classification.getSelectedIndex()];
        String date1 = date.getText();
        String book_copies = copies.getText();
        String book_edition = edition.getText();
        String shelff = shelf.getText();
        String deckk = deck.getText();

        if (booktitle.getText().equals("") || author.getText().equals("") || publisher.getText().equals("") || classification.getSelectedItem().toString().equals("") || date.getText().equals("") || copies.getText().equals("") || isbn.getText().equals("") || date.getText().equals("") || deck.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Field is empty", "Error", ERROR_MESSAGE);
        } else {
            if (upload) {
                if (this.localFilePath.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: Cover is empty", "Error", ERROR_MESSAGE);
                } else {
                    storage uploader = new storage(this.localFilePath, this.remoteFilePath);
                    try {
                        downloadUrl = uploader.upload();
                        GlassPanePopup.showPopup(new loading());
                    } catch (IOException ex) {
                        Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            String getnow = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            String uidpath = new getUID().getUid();
            String call_no = genr + "-" + shelff + deckk + "-" + date1;

            v = new pushValue(ck);
            m = new HashMap<>();
            m.put("booktitle", book_title);
            m.put("bookauthor", book_author);
            m.put("publisher", publ);
            m.put("isbn", book_isbn);
            m.put("classification", genr);
            m.put("classification_code", genr);
            m.put("classification_pos", classification.getSelectedIndex());
            m.put("classification", classification.getSelectedItem().toString());
            m.put("date", date1);
            m.put("copies", book_copies);
            m.put("edition", book_edition);
            m.put("shelf", shelff);
            m.put("deck", deckk);
            m.put("key", ck);
            m.put("call_number", call_no);
            m.put("status", "Available");
            m.put("remaining_copies", remaining_copies);
            m.put("timestamp", getnow);
            m.put("cover", downloadUrl);
            v.pushData("books/" + uidpath, m);
            GlassPanePopup.closePopupAll();
        }
    }//GEN-LAST:event_myButtonborderless2ActionPerformed

    private void photoCover1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_photoCover1MouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            this.localFilePath = selectedFile.getAbsolutePath();
            this.remoteFilePath = "cover/" + selectedFile.getName();

            try {
                BufferedImage image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
                photoCover1.setImage(image);
                upload = true;
            } catch (IOException ex) {
                Logger.getLogger(add_book.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_photoCover1MouseClicked

    private void dateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_dateMouseClicked

    private void booktitleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_booktitleKeyReleased
        // TODO add your handling code here:
        String text = booktitle.getText();
        String str = "";
        // Capitalize the first letter of the text
        if (text.length() > 0) {
            text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
            booktitle.setText(text);
        }

        if (text.length() > 100) {
            str = text.substring(0, 24);
            booktitle.setText("");
        }

        if (text.length() == 0) {
            booktitle.setText(str);
            str = "";
        }
    }//GEN-LAST:event_booktitleKeyReleased

    private void copiesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_copiesKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }//GEN-LAST:event_copiesKeyTyped

    private void isbnKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_isbnKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_isbnKeyTyped

    private void shelfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shelfKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }//GEN-LAST:event_shelfKeyTyped

    private void authorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_authorKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isLetter(c) || c == ' ' || c == '-' || c == '.' || c == ',' || c == '\'' || c == '\"'
                || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }//GEN-LAST:event_authorKeyTyped

    private void myButtonborder1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButtonborder1ActionPerformed
        // TODO add your handling code here:
        GlassPanePopup.closePopupAll();
    }//GEN-LAST:event_myButtonborder1ActionPerformed

    private void editionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editionActionPerformed

    private void editionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_editionKeyTyped

    private void deckKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deckKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }//GEN-LAST:event_deckKeyTyped

    private void deckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deckActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        System.out.println(books2.getRef());
        books2.getRef().removeValue(completionListener);
        GlassPanePopup.closePopupAll();
        removeAll();
        revalidate();
        model.setRowCount(0);
        retrieveData();
        repaint();
    }//GEN-LAST:event_deleteActionPerformed

    private void classificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classificationActionPerformed
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null, comboBoxSuggestion1, "Select", JOptionPane.QUESTION_MESSAGE);
    }//GEN-LAST:event_classificationActionPerformed

    private void myButtonborderless3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButtonborderless3ActionPerformed
        // TODO add your handling code here:
        GlassPanePopup.closePopupAll();
        GlassPanePopup.showPopup(new select_user(ck, title));
    }//GEN-LAST:event_myButtonborderless3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel allowedtype;
    private javax.swing.JTextField author;
    private javax.swing.JLabel authorlabel;
    private javax.swing.JTextField booktitle;
    private javax.swing.JLabel booktitlelabel;
    private libratech.design.ComboBoxSuggestion classification;
    private javax.swing.JLabel classificationlabel;
    private javax.swing.JTextField copies;
    private javax.swing.JLabel copieslabel;
    private javax.swing.JLabel coverlabel;
    private javax.swing.JTextField date;
    private javax.swing.JLabel datelabel;
    private javax.swing.JTextField deck;
    private javax.swing.JLabel decklabel;
    private libratech.design.MyButtonborder delete;
    private javax.swing.JTextField edition;
    private javax.swing.JLabel editionlabel;
    private javax.swing.JTextField isbn;
    private javax.swing.JLabel isbnlabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private libratech.design.MyButtonborder myButtonborder1;
    private libratech.design.MyButtonborderless myButtonborderless2;
    private libratech.design.MyButtonborderless myButtonborderless3;
    private libratech.design.PhotoCover photoCover1;
    private javax.swing.JTextField publisher;
    private javax.swing.JLabel publisherlabel;
    private javax.swing.JTextField shelf;
    private javax.swing.JLabel shelflabel;
    // End of variables declaration//GEN-END:variables
    public void initFont() {
        jLabel2.setFont(new Font("Poppins Regular", Font.BOLD, 24));
        coverlabel.setFont(new Font("Poppins Regular", Font.BOLD, 14));
        allowedtype.setFont(new Font("Poppins Regular", Font.PLAIN, 10));
        booktitle.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        booktitlelabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        author.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        authorlabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        publisher.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        publisherlabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        copies.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        copieslabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        shelf.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        shelflabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        shelf.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        shelflabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        date.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        datelabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        deck.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        decklabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        editionlabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        edition.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        isbn.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        isbnlabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        classification.setFont(new Font("Poppins Regular", Font.PLAIN, 12));
        classificationlabel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        myButtonborder1.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        myButtonborderless2.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        myButtonborderless3.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        delete.setFont(new Font("Poppins Regular", Font.BOLD, 12));

    }
}
