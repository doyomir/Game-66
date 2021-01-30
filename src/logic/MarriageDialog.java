package logic;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;

public class MarriageDialog extends Dialog {

	protected Object result;
	protected Shell shlMarriage;
	protected int marriageScore;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MarriageDialog(Shell parent, int style, int marriageScore) {
		super(parent, style);
		setText("SWT Dialog");
		this.marriageScore = marriageScore;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlMarriage.open();
		shlMarriage.layout();
		Display display = getParent().getDisplay();
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shlMarriage.setLocation((screenSize.width - shlMarriage.getBounds().width) / 2, (screenSize.height - shlMarriage.getBounds().height) / 2);
		while (!shlMarriage.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlMarriage = new Shell(getParent(), SWT.TITLE);
		shlMarriage.setSize(187, 110);
		shlMarriage.setText("Marriage");
		
		Label marriageLabel = new Label(shlMarriage, SWT.BORDER | SWT.CENTER);
		marriageLabel.setBounds(63, 10, 55, 15);
		marriageLabel.setText(marriageScore + "!");
		
		Button btnOk = new Button(shlMarriage, SWT.NONE);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shlMarriage.dispose();
			}
		});
		btnOk.setBounds(53, 42, 75, 25);
		btnOk.setText("Ok");

	}
}
