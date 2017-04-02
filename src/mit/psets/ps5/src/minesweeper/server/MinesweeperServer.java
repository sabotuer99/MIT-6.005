/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

import lib6005.parser.ParseTree;
import lib6005.parser.UnableToParseException;
import minesweeper.board.Board;
import minesweeper.server.command.Command;
import minesweeper.server.command.CommandResult;
import minesweeper.server.command.parser.CommandParserImpl;
import minesweeper.server.command.resolver.CommandResolver;
import minesweeper.server.command.resolver.CommandResolverImpl;
import minesweeper.server.grammar.CommandGrammar;

/**
 * Multiplayer Minesweeper server.
 */
public class MinesweeperServer {

	// System thread safety argument
	// TODO Problem 5

	/** Default server port. */
	private static final int DEFAULT_PORT = 4444;
	/** Maximum port number as defined by ServerSocket. */
	private static final int MAXIMUM_PORT = 65535;
	/** Default square board size. */
	private static final int DEFAULT_SIZE = 10;

	/** Socket for receiving incoming connections. */
	private final ServerSocket serverSocket;
	/**
	 * True if the server should *not* disconnect a client after a BOOM message.
	 */
	private final boolean debug;
	private Board board;
	private int N;
	
	private synchronized void incPlayers(){
		N++;
		
		System.out.format("Player added! %s players currently connected%n", N);
	}
	
	private synchronized void decPlayers(){
		N--;
		
		System.out.format("Player left... boo :(  %s players currently connected%n", N);
	}

	// TODO: Abstraction function, rep invariant, rep exposure

	/**
	 * Make a MinesweeperServer that listens for connections on port.
	 * 
	 * @param port
	 *            port number, requires 0 <= port <= 65535
	 * @param debug
	 *            debug mode flag
	 * @throws IOException
	 *             if an error occurs opening the server socket
	 */
	public MinesweeperServer(int port, boolean debug) throws IOException {
		serverSocket = new ServerSocket(port);
		this.debug = debug;
	}

	/**
	 * Run the server, listening for client connections and handling them. Never
	 * returns unless an exception is thrown.
	 * 
	 * @throws IOException
	 *             if the main server socket is broken (IOExceptions from
	 *             individual clients do *not* terminate serve())
	 */
	public void serve() throws IOException {

		//System.out.println("Serve called... ");
		//System.out.println(board.toString());
		
		
		while (true) {
			// block until a client connects
			final Socket socket = serverSocket.accept();

			incPlayers();
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					// handle the client
					try {
						handleConnection(socket);
					} catch (IOException ioe) {
						ioe.printStackTrace(); // but don't terminate serve()
					} finally {
						
						decPlayers();
						
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	/**
	 * Handle a single client connection. Returns when client disconnects.
	 * 
	 * @param socket
	 *            socket where the client is connected
	 * @throws IOException
	 *             if the connection encounters an error or terminates
	 *             unexpectedly
	 */
	private void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		String welcome;
		synchronized(this){
			 welcome = String.format("Welcome to Minesweeper. Players: %d including you. Board: %d columns by %d rows. Type 'help' for help.", 
					N, board.getX(), board.getY());
		}
		out.println(welcome);
		
		
		try {
			boolean keepPlaying = true;

			// connection loop
			for (String line = in.readLine(); keepPlaying; line = in.readLine()) {

				// stupid readLine() cuts off the terminators which breaks
				// the parser... fucking hell
				if (line != null) {
					line += "\n";
				}

				ParseTree<CommandGrammar> tree;
				try {
					tree = new CommandParserImpl().parse(line);
				} catch (UnableToParseException e) {
					out.println("Invalid Input");
					continue;
				}

				CommandResolver resolver = new CommandResolverImpl();
				Command command = resolver.resolve(tree);

				CommandResult result = command.exectute(board);

				if (!debug || !result.getResponse().contains("BOOM!")) {
					keepPlaying = result.keepPlaying();
				}
				out.println(result.getResponse().replaceAll("[\\r\\n]*$", ""));

			}
		} finally {
			out.close();
			in.close();
		}
	}

	/**
	 * Start a MinesweeperServer using the given arguments.
	 * 
	 * <br>
	 * Usage: MinesweeperServer [--debug | --no-debug] [--port PORT] [--size
	 * SIZE_X,SIZE_Y | --file FILE]
	 * 
	 * <br>
	 * The --debug argument means the server should run in debug mode. The
	 * server should disconnect a client after a BOOM message if and only if the
	 * --debug flag was NOT given. Using --no-debug is the same as using no flag
	 * at all. <br>
	 * E.g. "MinesweeperServer --debug" starts the server in debug mode.
	 * 
	 * <br>
	 * PORT is an optional integer in the range 0 to 65535 inclusive, specifying
	 * the port the server should be listening on for incoming connections. <br>
	 * E.g. "MinesweeperServer --port 1234" starts the server listening on port
	 * 1234.
	 * 
	 * <br>
	 * SIZE_X and SIZE_Y are optional positive integer arguments, specifying
	 * that a random board of size SIZE_X*SIZE_Y should be generated. <br>
	 * E.g. "MinesweeperServer --size 42,58" starts the server initialized with
	 * a random board of size 42*58.
	 * 
	 * <br>
	 * FILE is an optional argument specifying a file pathname where a board has
	 * been stored. If this argument is given, the stored board should be loaded
	 * as the starting board. <br>
	 * E.g. "MinesweeperServer --file boardfile.txt" starts the server
	 * initialized with the board stored in boardfile.txt.
	 * 
	 * <br>
	 * The board file format, for use with the "--file" option, is specified by
	 * the following grammar:
	 * 
	 * <pre>
	 *   FILE ::= BOARD LINE+
	 *   BOARD ::= X SPACE Y NEWLINE
	 *   LINE ::= (VAL SPACE)* VAL NEWLINE
	 *   VAL ::= 0 | 1
	 *   X ::= INT
	 *   Y ::= INT
	 *   SPACE ::= " "
	 *   NEWLINE ::= "\n" | "\r" "\n"?
	 *   INT ::= [0-9]+
	 * </pre>
	 * 
	 * <br>
	 * If neither --file nor --size is given, generate a random board of size
	 * 10x10.
	 * 
	 * <br>
	 * Note that --file and --size may not be specified simultaneously.
	 * 
	 * @param args
	 *            arguments as described
	 */
	public static void main(String[] args) {
		// Command-line argument parsing is provided. Do not change this method.
		boolean debug = false;
		int port = DEFAULT_PORT;
		int sizeX = DEFAULT_SIZE;
		int sizeY = DEFAULT_SIZE;
		Optional<File> file = Optional.empty();

		Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
		try {
			while (!arguments.isEmpty()) {
				String flag = arguments.remove();
				try {
					if (flag.equals("--debug")) {
						debug = true;
					} else if (flag.equals("--no-debug")) {
						debug = false;
					} else if (flag.equals("--port")) {
						port = Integer.parseInt(arguments.remove());
						if (port < 0 || port > MAXIMUM_PORT) {
							throw new IllegalArgumentException("port " + port + " out of range");
						}
					} else if (flag.equals("--size")) {
						String[] sizes = arguments.remove().split(",");
						sizeX = Integer.parseInt(sizes[0]);
						sizeY = Integer.parseInt(sizes[1]);
						file = Optional.empty();
					} else if (flag.equals("--file")) {
						sizeX = -1;
						sizeY = -1;
						file = Optional.of(new File(arguments.remove()));
						if (!file.get().isFile()) {
							throw new IllegalArgumentException("file not found: \"" + file.get() + "\"");
						}
					} else {
						throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
					}
				} catch (NoSuchElementException nsee) {
					throw new IllegalArgumentException("missing argument for " + flag);
				} catch (NumberFormatException nfe) {
					throw new IllegalArgumentException("unable to parse number for " + flag);
				}
			}
		} catch (IllegalArgumentException iae) {
			System.err.println(iae.getMessage());
			System.err.println(
					"usage: MinesweeperServer [--debug | --no-debug] [--port PORT] [--size SIZE_X,SIZE_Y | --file FILE]");
			return;
		}

		try {
			runMinesweeperServer(debug, file, sizeX, sizeY, port);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	/**
	 * Start a MinesweeperServer running on the specified port, with either a
	 * random new board or a board loaded from a file.
	 * 
	 * @param debug
	 *            The server will disconnect a client after a BOOM message if
	 *            and only if debug is false.
	 * @param file
	 *            If file.isPresent(), start with a board loaded from the
	 *            specified file, according to the input file format defined in
	 *            the documentation for main(..).
	 * @param sizeX
	 *            If (!file.isPresent()), start with a random board with width
	 *            sizeX (and require sizeX > 0).
	 * @param sizeY
	 *            If (!file.isPresent()), start with a random board with height
	 *            sizeY (and require sizeY > 0).
	 * @param port
	 *            The network port on which the server should listen, requires 0
	 *            <= port <= 65535.
	 * @throws IOException
	 *             if a network error occurs
	 */
	public static void runMinesweeperServer(boolean debug, Optional<File> file, int sizeX, int sizeY, int port)
			throws IOException {

		// TODO: Continue implementation here in problem 4
		Board board = randomBoard(sizeX, sizeY);
		if(file.isPresent()){
			board = parseFile(file.get());
		}

		MinesweeperServer server = new MinesweeperServer(port, debug);
		server.setBoard(board);
		
		System.out.format("Starting Minesweeper server on port %s%nBoard size %s x %s, bomb locations:  %n", port, board.getX(), board.getY());
		System.out.println(board.toDebugString());
		
		server.serve();
	}

	private static Board randomBoard(int cols, int rows) {
		
		if(cols <= 0 || rows <= 0){
			return randomBoard(10,10);
		}
		
		int[][] vals = new int[cols][rows];
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				double roll = Math.random();
				if(roll < 0.2){
					vals[row][col] = 1;
				}
			}
		}
		
		return new Board(vals);
	}

	private void setBoard(Board board) {
		this.board = board;
	}
	
	private static Board parseFile(File file){
		Board board = randomBoard(1,1);
		try {
			Scanner sc = new Scanner(Files.newBufferedReader(file.toPath()));

			int cols = sc.nextInt();
			int rows = sc.nextInt();
			
			int[][] vals = new int[rows][cols];
			
			for(int row = 0; row < rows; row++){
				for(int col = 0; col < cols; col++){
					vals[row][col] = sc.nextInt();
				}
			}
			
			board = new Board(vals);
			
			sc.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Parsed the following board from file: " + file.getName());
		System.out.println(board.toString());
		
		return board;
	}

}
