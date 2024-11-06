package com.keyin;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;

public class MetrobusClient {

    private static final String BASE_URL = "http://localhost:8080/api/mcard";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\nMetrobus Client Menu:");
            System.out.println("1. Issue new mCard");
            System.out.println("2. Get all mCards");
            System.out.println("3. Top up mCard");
            System.out.println("4. Pay for ride");
            System.out.println("5. Check mCard balance");
            System.out.println("6. Get mCard history");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    issueNewMCard();
                    break;
                case 2:
                    getAllMCards();
                    break;
                case 3:
                    topUpMCard();
                    break;
                case 4:
                    payForRide();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    getMCardHistory();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static void issueNewMCard() throws Exception {
        System.out.print("Enter card number to issue: ");
        String cardNumber = scanner.nextLine();

        String url = BASE_URL + "/issue?cardNumber=" + cardNumber;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONObject jsonResponse = new JSONObject(response.toString());

                System.out.println("New MCard Issued:");
                System.out.println("ID: " + jsonResponse.getInt("id"));
                System.out.println("Card Number: " + jsonResponse.getString("cardNumber"));
                System.out.println("Balance: " + jsonResponse.getDouble("balance"));
                System.out.println("Issued At: " + jsonResponse.getString("issuedAt"));
            }
        } else {
            System.out.println("Error: " + status);
        }
    }


    private static void getAllMCards() throws Exception {
        String url = BASE_URL + "/all";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int status = connection.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonResponseArray = new JSONArray(response.toString());

            System.out.println("List of MCards:");

            for (int i = 0; i < jsonResponseArray.length(); i++) {
                JSONObject jsonResponse = jsonResponseArray.getJSONObject(i);
                System.out.println("Card " + (i + 1) + ":");
                System.out.println("  ID: " + jsonResponse.getInt("id"));
                System.out.println("  Card Number: " + jsonResponse.getString("cardNumber"));
                System.out.println("  Balance: " + jsonResponse.getDouble("balance"));
                System.out.println("  Issued At: " + jsonResponse.getString("issuedAt"));
                System.out.println();
            }
        } else {
            System.out.println("Error: " + status);
        }
    }

    private static void topUpMCard() throws Exception {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter amount to top up: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        String url = BASE_URL + "/topup/" + cardNumber + "?amount=" + amount;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        int status = connection.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            System.out.println("Top-up Successful!");
            System.out.println("-----------------------------");
            System.out.println("Card ID: " + jsonResponse.getInt("id"));
            System.out.println("Card Number: " + jsonResponse.getString("cardNumber"));
            System.out.println("New Balance: " + jsonResponse.getDouble("balance"));
            System.out.println("Issued At: " + jsonResponse.getString("issuedAt"));
            System.out.println("-----------------------------");
        } else {
            System.out.println("Error: " + status);
        }
    }

    private static void payForRide() throws Exception {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter fare amount: ");
        double fare = scanner.nextDouble();
        scanner.nextLine();

        String url = BASE_URL + "/pay/" + cardNumber + "?fare=" + fare;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        int status = connection.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            System.out.println("Ride Payment Successful!");
            System.out.println("-----------------------------");
            System.out.println("Card ID: " + jsonResponse.getInt("id"));
            System.out.println("Card Number: " + jsonResponse.getString("cardNumber"));
            System.out.println("Remaining Balance: " + jsonResponse.getDouble("balance"));
            System.out.println("Issued At: " + jsonResponse.getString("issuedAt"));
            System.out.println("-----------------------------");
        } else {
            System.out.println("Error: " + status);
        }
    }

    private static void checkBalance() throws Exception {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();

        String url = BASE_URL + "/balance/" + cardNumber;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int status = connection.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Remaining balance: " + response.toString());
        } else {
            System.out.println("Error: " + status);
        }
    }

    private static void getMCardHistory() throws Exception {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();

        String url = BASE_URL + "/history/" + cardNumber;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int status = connection.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray historyArray = new JSONArray(response.toString());

            if (!historyArray.isEmpty()) {
                JSONObject firstEntry = historyArray.getJSONObject(0);
                JSONObject mcard = firstEntry.getJSONObject("mcard");

                System.out.println("Card Details:");
                System.out.println("-----------------------------");
                System.out.println("Card ID: " + mcard.getInt("id"));
                System.out.println("Card Number: " + mcard.getString("cardNumber"));
                System.out.println("Balance: " + mcard.getDouble("balance"));
                System.out.println("Issued At: " + mcard.getString("issuedAt"));
                System.out.println("-----------------------------\n");

                System.out.println("Ride History:");
                for (int i = 0; i < historyArray.length(); i++) {
                    JSONObject ride = historyArray.getJSONObject(i);
                    System.out.println("Ride ID: " + ride.getInt("id"));
                    System.out.println("Fare: $" + ride.getDouble("fare"));
                    System.out.println("Ride Date: " + ride.getString("rideAt"));
                    System.out.println("-----------------------------");
                }
            } else {
                System.out.println("No history found for this card.");
            }
        } else {
            System.out.println("Error: " + status);
        }
    }
}
