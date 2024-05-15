import java.util.*;
class Group
{
    public static void main(String agrs[])
    {
        Tiktactoe t=new Tiktactoe();
        Hengmen h=new Hengmen();
        Anothergame a=new Anothergame();
        boolean b=true;
        System.out.println("Hello sir!!!");
        System.out.println("WELCOME TO OUR PROGRAME OF PUZZLE GAME ");
        Scanner sin=new Scanner(System.in);
        while(b)
        {
        System.out.println("ENTER 1 IF YOU WANT TO PLAY TICTACTOE");
        System.out.println("ENTER 2 IF YOU WANT TO PLAY HENGMAN");
        System.out.println("ENTER 3 IF YOU WANT TO EXIT!");
        int ch=sin.nextInt();
        switch(ch)
        {
            case 1:
            {
                t.tic();
                break;
            }
            case 2:
            {
                h.hang();
                break;
            }
            case 3:
            {
                b=false;
                break;
            } 
            default:
            {
                System.out.println("Enter valid Number!!");
                break;
            }
        }
        }
        System.out.println("Thanks for playing");
    }
}
//----------------------------------------------------------------------------------------------------------------------
class Tiktactoe
{
    static String[] board=new String[9];
    static String turn="X";
    String winner = null;
    Scanner in=new Scanner(System.in);
    void tic()
    {
    
       System.out.println("--------------------------TIC-TAC-TOE-GAME STARTED--------------------------");
        player();

        for (int a = 0; a < 9; a++)
        {
            board[a] = String.valueOf(a + 1);
        }
        System.out.println("Welcome to 3x3 Tic Tac Toe.");
        printBoard();
        System.out.println( "X will play first. Enter a slot number to place X in:");
			while (winner == null)
             {
           		int numInput;
		numInput = in.nextInt();
            // This game has two player x and O.
            // Here is the logic to decide the turn.
            if (board[numInput - 1].equals(String.valueOf(numInput))) {
                board[numInput - 1] = turn;
                if (turn.equals("X"))
                 {
                    turn = "O";
                }
                else 
                {
                    turn = "X";
                }
                printBoard();
                winner = checkWinner();
            }
            else 
            {
                System.out.println("Slot already taken; re-enter slot number:");
            }
        }
        // If no one win or lose from both player x and O.

        // then here is the logic to print "draw".

        if (winner.equalsIgnoreCase("draw")) {

            System.out.println(

                "It's a draw! Thanks for playing.");
        }
        // For winner -to display Congratulations! message.
        else
         {
            System.out.println( "Congratulations! " + winner+ "'s have won! Thanks for playing.");
        }
}
   public static String checkWinner()
    {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
            case 0:line = board[0] + board[1] + board[2];
             break;
            case 1:
                line = board[3] + board[4] + board[5];
                break;
            case 2:
                line = board[6] + board[7] + board[8];
                break;
            case 3:
                line = board[0] + board[3] + board[6];
                break;
            case 4:
                line = board[1] + board[4] + board[7];
                break;
            case 5:
                line = board[2] + board[5] + board[8];
                break;
            case 6:
                line = board[0] + board[4] + board[8];
                break;
            case 7:
                line = board[2] + board[4] + board[6];
                break;
            }
            //For X winner
            if (line.equals("XXX")) 
            {
                return fp;
            }
            // For O winner
            else if (line.equals("OOO")) 
            {
                return sp;
            }
        }
        int s=0;
		for(int a=0;a<9;a++)
		{
			if(board[a].equals("O")||board[a].equals("X"))
			{
				s++;
			}
		}
		if(s==9)
		{
			return("DRAW");
		}
        System.out.println(turn + "'s turn; enter a slot number to place "+ turn + " in:");
        return null;
    }
    // To print out the board.

    /*              

        1 | 2 | 3 

       -----------

        4 | 5 | 6 

       -----------

        7 | 8 | 9 
		

       */

   static void printBoard()
    {
        System.out.println("            ");

        System.out.println("  " +  board[0] + " | "+ board[1] + " | " + board[2]+ "  ");
        System.out.println(" ----------- ");
        System.out.println("  " + board[3] + " | "+ board[4] + " | " + board[5]+ "  ");
        System.out.println(" ----------- ");
        System.out.println("  " + board[6] + " | "+ board[7] + " | " + board[8]+ "  ");
        System.out.println("             ");
    }
	static String fp,sp;
    static void player()
	{
		Scanner sin=new Scanner(System.in);
		System.out.println(" X - FIRST PLAYER");
		System.out.println(" O - SECOND PLAYER");
		System.out.println("ENTER FIRST PLAYER NAME");
		fp=sin.nextLine();
		System.out.println("ENTER SECOND PLAYER NAME ");
		sp=sin.nextLine();
		System.out.println("LET'S START THE GAME");
	}	
}
class Hengmen
{
     static int count = 0;
	private static String words[] = {"banana", "apple", "orange", "watermelon", "grapes", "pomegranate" };
	private static String word = words[(int) (Math.random() * words.length)];
    static String asterisk = new String(new char[word.length()]).replace("\0", "*");
    Scanner sc=new Scanner(System.in);
    void hang()
    {
        while (count < 7 && asterisk.contains("*"))
		{		
			System.out.println("Guess any letter in the word");
			System.out.println(asterisk);
			String guess = sc.next();
			hang(guess);
		}
    }
    public static void hang(String guess) {
		String asterisk2 = "";
		for (int i = 0; i < word.length(); i++) 
        {
			if (word.charAt(i) == guess.charAt(0))
           {
				asterisk2 += guess.charAt(0);
			} 
            else if (asterisk.charAt(i) != '*') 
            {
				asterisk2 += word.charAt(i);
			} 
            else
             {
				asterisk2 += "*";
			}
		}

		if (asterisk.equals(asterisk2)) {
			count++;
			hangmanImage();
		} else {
			asterisk = asterisk2;
		}
		if (asterisk.equals(word)) {
			System.out.println("Yayy!  correct guess! You win! The word was " + word);
		}
	}

	public static void hangmanImage() {
		if (count == 1) {
			System.out.println("Sorry! wrong guess,try again ");
			System.out.println("You have only 6 try left");
			System.out.println();
			System.out.println("|");
			System.out.println();
		}
		if (count == 2) {
			System.out.println("Sorry! wrong guess,try again");
            System.out.println("You have only 5 try left");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("  |");
		}
		if (count == 3) {
			System.out.println("Sorry! wrong guess,try again");
            System.out.println("You have only 4 try left");
			System.out.println("   ___");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   | ");
			System.out.println("  |");
		}
		if (count == 4) {
			System.out.println("Sorry! wrong guess,try again");
            System.out.println("You have only 3 try left");
			System.out.println("   __");
			System.out.println("   |          |");
			System.out.println("   |         /   \\");
			System.out.println("   |        |     |");
			System.out.println("   |         \\_ _/");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("   |");
			System.out.println("|");
		}
		if (count == 5) {
			System.out.println("Sorry! wrong guess,try again");
            System.out.println("You have only 2 try left");
			System.out.println("   _____");
			System.out.println("   |          |");
			System.out.println("   |         /   \\");
			System.out.println("   |        |     |");
			System.out.println("   |         \\_ _/");
			System.out.println("   |           |");
			System.out.println("   |           |");
			System.out.println("   |");
			System.out.println("  |");
		}
		if (count == 6) {
			System.out.println("Sorry! wrong guess,try again");
            System.out.println("You have only 1 try left");
			System.out.println("   ______");
			System.out.println("   |           |");
			System.out.println("   |         /   \\");
			System.out.println("   |        |     |");
			System.out.println("   |         \\_ _/");
			System.out.println("   |           |");
			System.out.println("   |           |");
			System.out.println("   |          / \\ ");
			System.out.println("  |        /   \\");
		}
		if (count == 7) {
			System.out.println("GAME OVER!");
			System.out.println("   _____");
			System.out.println("   |           |");
			System.out.println("   |         /   \\");
			System.out.println("   |        |     |");
			System.out.println("   |         \\_ _/");
			System.out.println("   |           |");
			System.out.println("   |         / | \\");
			System.out.println("   |          / \\ ");
			System.out.println("  |        /   \\");
			System.out.println("GAME OVER! The word was " + word);
			System.out.println("Thank you for playing");
			
		}
	}
}