//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.util.Arrays;
//
//public class UI extends JPanel implements MouseListener, MouseMotionListener {
//    static int square = 32;
//    static int originalX;
//    static int originalY;
//    static int updatedX;
//    static int updatedY;
//
//
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        this.setBackground(Color.yellow);
//        this.addMouseListener(this);
//        this.addMouseMotionListener(this);
//        for(int i =0;i<64;i+=2){
//            g.setColor(new Color(255,200,100));
//            g.fillRect((i%8+(i/8)%2)*square,(i/8)*square,square,square);
//            g.setColor(new Color(150,50,30));
//            g.fillRect(((i+1)%8-((i+1)/8)%2)*square,((i+1)/8)*square,square,square);
//        }
//        Image chessPieces;
//        chessPieces = new ImageIcon("ChessPieces.png").getImage();
//        for(int i=0;i<64;i++){
//            int j = -1;
//            int k = -1;
//            switch (Chess.Board[i/8][i%8]){
//                case "P":
//                    j =5;
//                    k =0;
//                    break;
//                case"p":
//                    j = 5;
//                    k=1;
//                    break;
//                case "R":
//                    j =2;
//                    k =0;
//                    break;
//                case"r":
//                    j =2;
//                    k=1;
//                    break;
//                case "K":
//                    j =4;
//                    k =0;
//                    break;
//                case"k":
//                    j =4;
//                    k=1;
//                    break;
//                case "B":
//                    j = 3;
//                    k =0;
//                    break;
//                case"b":
//                    j =3;
//                    k=1;
//                    break;
//                case "Q":
//                    j =1;
//                    k =0;
//                    break;
//                case"q":
//                    j =1;
//                    k=1;
//                    break;
//                case "A":
//                    j =0;
//                    k =0;
//                    break;
//                case"a":
//                    j =0;
//                    k=1;
//                    break;
//            }
//            if(j!=-1 && k!=-1){
//                g.drawImage(chessPieces,(i%8)*square,(i/8)*square,(i%8+1)*square,(i/8+1)*square,j*64,k*64,(j+1)*64,(k+1)*64,this);
//            }
//        }
//    }
//    public static void GameOver(){
//        JOptionPane.showMessageDialog(null,"CheckMate! Game Over!");
//    }
//    public static void StaleMate(){
//        JOptionPane.showMessageDialog(null,"StaleMate! Game Over!");
//    }
//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
////        System.out.println("Pressing");
//        if(e.getX()<8*square && e.getY()<8*square ){
//            originalX = e.getX();
//            originalY=e.getY();
//            repaint();
//        }
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
////        System.out.println("Released");
//        if(e.getX()<8*square && e.getY()<8*square ){
//            updatedX = e.getX();
//            updatedY=e.getY();
//            if(e.getButton()==MouseEvent.BUTTON1){
//                String drag;
//                if(updatedY/square==0 && originalY/square==1 && "P"==Chess.Board[originalY/square][originalX/square]){
//                    drag=""+originalX/square+updatedX/square+Chess.Board[updatedY/square][updatedX/square]+"QP";
//                }
//                else{
//                    drag=""+originalY/square+originalX/square+updatedY/square+updatedX/square+Chess.Board[updatedY/square][updatedX/square];
//                }
//                String userMoves=Chess.movesPossible();
////                System.out.println("Printing");
//                System.out.println(userMoves);
////                for (int i=0;i<8;i++) {
////                    System.out.println(Arrays.toString(AlphaBetaChess.chessBoard[i]));
////                }
//                if (userMoves.replaceAll(drag, "").length()<userMoves.length()) {
//                    //if valid move
//                    Chess.makeMove(drag);
//                    Chess.frame.setTitle("User's turn");
//                    Chess.flipBoard();
//                    Chess.makeMove(Chess.alphaBeta(Chess.maxDepth, -1000000, 1000000, "", 0));
//                    Chess.flipBoard();
//                    repaint();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//
//    }
//}
