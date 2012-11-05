import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class ReservationManager extends JFrame implements ActionListener{

	 // Main Container
    private Container mContainer;
    
    private JPanel mReservationPannel;
    private JTable mReservationTable;
    private DefaultTableModel mTableModel;
    private JButton mSendSMS;
    
    private Vector<Vector<String>> mReservationList = new Vector<Vector<String>>();;
    public Vector<Vector<String>> getmReservationList() {
		return mReservationList;
	}

	private Vector<String> columnNames = new Vector<String>();
	
	public ReservationManager() {	
		// TODO Auto-generated constructor stub
		super("Android NFC Test Tool");
        mContainer = getContentPane();
        mContainer.setLayout(new FlowLayout());        
        setSize(460, 400);
        setLocation(400, 250);
                
        mReservationPannel = new JPanel(new BorderLayout());
        mReservationPannel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Reservation List."));
        mReservationPannel.setPreferredSize(new Dimension(400,300));
        
        columnNames.add("No.");
        columnNames.add("Phone Number");
        
        mTableModel = new DefaultTableModel(mReservationList, columnNames);
        
        mReservationTable = new JTable(mTableModel);
        mReservationTable.getColumnModel().getColumn(0).setPreferredWidth(40);        
        mReservationTable.setRowSelectionAllowed(true);
        mReservationPannel.add(mReservationTable.getTableHeader(), BorderLayout.PAGE_START);
        mReservationPannel.add(mReservationTable, BorderLayout.CENTER);
        mContainer.add(mReservationPannel); 
        
        mSendSMS = new JButton("Send SMS");
        mSendSMS.setPreferredSize(new Dimension(380, 50));
        mSendSMS.addActionListener(this);
        mSendSMS.setEnabled(true);
        mContainer.add(mSendSMS); 
        
        setResizable(false);
        setVisible(true); 
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReservationManager rm = new ReservationManager();
		rm.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Vector<String> aa = new Vector<String>();
		aa.add("1");aa.add("222");
		mReservationList.add(aa);
		System.out.println(aa);
		mTableModel.fireTableDataChanged();
		mReservationTable.requestFocusInWindow();
	}

}
