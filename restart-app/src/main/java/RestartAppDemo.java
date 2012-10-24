import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

/**
 * Sometimes it is required to make your application restart on its own. For
 * example, IntelliJ Idea after downloading the new plugins requires to be
 * restarted. But it can't restart itself, so asks the user to restart after its
 * exit. Let us see how to make you application restart on its own.
 * 
 * @author idanilov
 * 
 */
public class RestartAppDemo {

	public static void main(String args[]) {
		Action restartAction = new AbstractAction("Restart") {

			public void actionPerformed(ActionEvent ae) {
				try {
					System.exit(100);// restart.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		JFrame frame = new JFrame(RestartAppDemo.class.getSimpleName());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.add(restartAction);
		menubar.add(menu);
		frame.setJMenuBar(menubar);
		frame.setSize(200, 100);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
