/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package libratech.dashboard;

import java.awt.Font;
import libratech.design.GlassPanePopup;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.AWTEvent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import libratech.auth.signup;
import libratech.books.inshelf.Book;
import libratech.books.inshelf.EventAction;
import libratech.books.inshelf.StatusType;
import libratech.books.inshelf.TableStatus;
import libratech.design.ImageScaler;
import libratech.design.RoundedPanel;
import libratech.design.loading;
import libratech.models.getUID;
import libratech.models.pushValue;
import libratech.util.firebaseInit;
import libratech.util.storage;

/**
 *
 * @author HBUSER
 */
public class user_menu extends javax.swing.JPanel {

    private List<Book> books;
    private DatabaseReference dbRef;
    private DatabaseReference dbRef1;
    private DatabaseReference dbRef2;
    DefaultTableModel mod;
    DefaultTableModel mod1;
    private String path = "analytics/" + new getUID().getUid() + "/";
    private DatabaseReference analytics = FirebaseDatabase.getInstance().getReference(path);
    private HashMap<String, Object> m;
    private pushValue v;
    ImageScaler scaler = new ImageScaler();
    boolean exist;
    private String localFilePath;
    private String remoteFilePath;
    private DatabaseReference databaseReference;

    public user_menu() {
        initComponents();
        initFont();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.mod = (DefaultTableModel) inshelfTable1.getModel();
        new firebaseInit().initFirebase();
        inshelfTable1.fixTable(jScrollPane1);
        retrieveData();

        String filePath = "uid.txt";
        File file = new File(filePath);

        scaler.scaleImage(jLabel20, "src\\main\\resources\\search-line.png");

        if (!file.exists()) {
            myButtonborderless1.setVisible(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon("src\\main\\resources\\bgc.png");
        Image image = imageIcon.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    ;
    
     private void retrieveData() {
        EventAction eventAction = new EventAction() {
            @Override
            public void update(Book book) {
               // try {
                    String filename = book.getFilename();
                    GlassPanePopup.showPopup(new view_pdf(book.getFile(), book.getFilename(), book.getKey()));
                    //new storage().download(filename);
//                } catch (IOException ex) {
//                    Logger.getLogger(books_menu.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        };

        dbRef = FirebaseDatabase.getInstance().getReference("files/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mod.setRowCount(0);

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("filename").getValue(String.class).contains("BP#")) {
                        String key = child.child("key").getValue(String.class);
                        String filename = child.child("filename").getValue(String.class);
                        String file_created = child.child("filecreated").getValue(String.class);
                        String file = child.child("file").getValue(String.class);

                        inshelfTable1.addRow(new Book(filename, file_created, file, key).toRowTableFiles(eventAction));
                        new Book().setChildKey(key);
                        mod.fireTableDataChanged();
                        inshelfTable1.repaint();
                        inshelfTable1.revalidate();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        myButtonborderless1 = new libratech.design.MyButtonborderless();
        jPanel26 = new RoundedPanel(12, new Color(245,245,245));
        search18 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inshelfTable1 = new libratech.books.inshelf.InshelfTable();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(224, 224, 224,0));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout(30, 10));

        jPanel8.setBackground(new java.awt.Color(224, 224, 224,0));
        jPanel8.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(58, 58, 58));
        jLabel1.setText("Building Permit");

        myButtonborderless1.setForeground(new java.awt.Color(250, 250, 250));
        myButtonborderless1.setText("Upload File");
        myButtonborderless1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                myButtonborderless1MouseClicked(evt);
            }
        });
        myButtonborderless1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButtonborderless1ActionPerformed(evt);
            }
        });

        jPanel26.setBackground(new java.awt.Color(0, 0, 0));
        jPanel26.setOpaque(false);

        search18.setBackground(new java.awt.Color(245, 245, 245));
        search18.setBorder(null);
        search18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search18ActionPerformed(evt);
            }
        });
        search18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                search18KeyTyped(evt);
            }
        });

        jLabel20.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search18, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel26.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 501, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myButtonborderless1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(myButtonborderless1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(224, 224, 224));
        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        inshelfTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File Name", "Date Created", "Actions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(inshelfTable1);

        jPanel9.add(jScrollPane1);

        jPanel1.add(jPanel9, java.awt.BorderLayout.CENTER);
        jPanel1.add(filler1, java.awt.BorderLayout.LINE_START);
        jPanel1.add(filler2, java.awt.BorderLayout.LINE_END);
        jPanel1.add(filler3, java.awt.BorderLayout.PAGE_END);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void myButtonborderless1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myButtonborderless1MouseClicked
        // TODO add your handling code here:
        GlassPanePopup.showPopup(new add_book());
    }//GEN-LAST:event_myButtonborderless1MouseClicked

    private void myButtonborderless1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButtonborderless1ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            this.localFilePath = selectedFile.getAbsolutePath();
            this.remoteFilePath = "files/" + selectedFile.getName();

            storage uploader = new storage(this.localFilePath, this.remoteFilePath);
            try {
                String getnow = new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss.SSSZ").format(Calendar.getInstance().getTime());

                String downloadUrl = uploader.upload();
                String key = databaseReference.push().getKey();
                v = new pushValue(key);
                m = new HashMap<>();
                m.put("filename", selectedFile.getName());
                m.put("filecreated", getnow);
                m.put("file", downloadUrl);
                m.put("key", key);
                v.pushData("files", m);
                GlassPanePopup.showPopup(new upload_successful());
            } catch (IOException ex) {
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_myButtonborderless1ActionPerformed

    private void search18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search18ActionPerformed

    private void search18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search18KeyTyped
        // TODO add your handling code here:
        EventAction eventAction = new EventAction() {
            @Override
            public void update(Book book) {
              // try {
                    String filename = book.getFilename();
                    GlassPanePopup.showPopup(new view_pdf(book.getFile(), book.getFilename(), book.getKey()));
                    //new storage().download(filename);
//                } catch (IOException ex) {
//                    Logger.getLogger(books_menu.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        };

        dbRef = FirebaseDatabase.getInstance().getReference("files/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mod.setRowCount(0);

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("filename").getValue(String.class).contains("BP#") && child.child("filename").getValue(String.class).toLowerCase().contains(search18.getText())) {
                        String key = child.child("key").getValue(String.class);
                        String filename = child.child("filename").getValue(String.class);
                        String file_created = child.child("filecreated").getValue(String.class);
                        String file = child.child("file").getValue(String.class);

                        inshelfTable1.addRow(new Book(filename, file_created, file, key).toRowTableFiles(eventAction));
                        new Book().setChildKey(key);
                        mod.fireTableDataChanged();
                        inshelfTable1.repaint();
                        inshelfTable1.revalidate();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }//GEN-LAST:event_search18KeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    public libratech.books.inshelf.InshelfTable inshelfTable1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private libratech.design.MyButtonborderless myButtonborderless1;
    private javax.swing.JTextField search18;
    // End of variables declaration//GEN-END:variables
    public void initFont() {
        jLabel1.setFont(new Font("Poppins Regular", Font.BOLD, 24));
        myButtonborderless1.setFont(new Font("Poppins Regular", Font.BOLD, 14));
        search18.setFont(new Font("Poppins Regular", Font.PLAIN, 12));

    }

}
