package amf;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;

public class AmfTestController extends HttpServlet {

	private static final long serialVersionUID = 7493800458799696603L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SerializationContext context = new SerializationContext();
		Amf3Input amfin = new Amf3Input(context);
		amfin.setInputStream(req.getInputStream());
		
		User user = null;
		try {
			user = (User) amfin.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		out.write(user.getName() + ": " + user.getJob().getTitle() + "\n\n");
		for (String friend : user.getFriends()) {
			out.write(friend + ", ");
		}
		out.write("\n");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
