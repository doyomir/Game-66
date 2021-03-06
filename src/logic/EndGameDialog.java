package logic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import enums.Winner;

public class EndGameDialog extends Dialog {

	protected Object result;
	protected Shell shlEndGame;
	protected int scorePlayer;
	protected int scoreComputer;
	protected Winner winner;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EndGameDialog(Shell parent, int style, int scorePlayer, int scoreComputer, Winner winner) {		
		super(parent, style);
		setText("SWT Dialog");
		this.scorePlayer = scorePlayer;
		this.scoreComputer = scoreComputer;
		this.winner = winner;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {		
		createContents();
		shlEndGame.open();
		shlEndGame.layout();
		Display display = getParent().getDisplay();
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shlEndGame.setLocation((screenSize.width - shlEndGame.getBounds().width) / 2, (screenSize.height - shlEndGame.getBounds().height) / 2);
		while (!shlEndGame.isDisposed()) {
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
		shlEndGame = new Shell(getParent(), getStyle());
		shlEndGame.setSize(270, 218);
		shlEndGame.setText("Game Over");
		
		
		String winLoseText = (winner == Winner.PLAYER_WINNER) ? "You Win!" : "You Lose!";
		if (scorePlayer == scoreComputer) {
			winLoseText = "It's a tie!";
		}
		
		String imagePath = (winner == Winner.PLAYER_WINNER) ? "/resources/drawable-hdpi/happy.png" : "/resources/drawable-hdpi/unhappy.png";
		
		Button btnOk = new Button(shlEndGame, SWT.NONE);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shlEndGame.dispose();
			}
		});
		btnOk.setBounds(94, 151, 75, 25);
		btnOk.setText("Ok");
		
		Label lblScore = new Label(shlEndGame, SWT.CENTER);
		lblScore.setBounds(2, 37, 260, 15);
		lblScore.setText("Your score is: " + scorePlayer + " Computer Score is: " + scoreComputer);
		
		CLabel lblImage = new CLabel(shlEndGame, SWT.CENTER);
		lblImage.setBounds(89, 58, 86, 91);
		lblImage.setImage(SWTResourceManager.getImage(Game66.class, imagePath));
		
		Label lblWinLose = new Label(shlEndGame, SWT.CENTER);
		lblWinLose.setText(winLoseText);
		lblWinLose.setBounds(2, 10, 260, 15);

	}


	
}
