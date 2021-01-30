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

public class PairDialog extends Dialog {

	protected Object result;
	protected Shell shlPair;
	protected int pairScore;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PairDialog(Shell parent, int style, int pairScore) {
		super(parent, style);
		setText("SWT Dialog");
		this.pairScore = pairScore;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlPair.open();
		shlPair.layout();
		Display display = getParent().getDisplay();
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shlPair.setLocation((screenSize.width - shlPair.getBounds().width) / 2, (screenSize.height - shlPair.getBounds().height) / 2);
		while (!shlPair.isDisposed()) {
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
		shlPair = new Shell(getParent(), SWT.TITLE);
		shlPair.setSize(187, 110);
		shlPair.setText("Pair");
		
		Label lblNewLabel = new Label(shlPair, SWT.BORDER | SWT.CENTER);
		lblNewLabel.setBounds(63, 10, 55, 15);
		lblNewLabel.setText(pairScore + "!");
		
		Button btnOk = new Button(shlPair, SWT.NONE);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shlPair.dispose();
			}
		});
		btnOk.setBounds(53, 42, 75, 25);
		btnOk.setText("Ok");

	}
}
