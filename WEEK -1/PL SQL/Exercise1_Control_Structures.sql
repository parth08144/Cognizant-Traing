
CREATE TABLE Customers (
    CustomerID      NUMBER PRIMARY KEY,
    Name            VARCHAR2(100),
    DOB             DATE,
    Balance         NUMBER(15, 2),
    IsVIP           VARCHAR2(5) DEFAULT 'FALSE'
);

-- Loans Table
CREATE TABLE Loans (
    LoanID          NUMBER PRIMARY KEY,
    CustomerID      NUMBER REFERENCES Customers(CustomerID),
    LoanAmount      NUMBER(15, 2),
    InterestRate    NUMBER(5, 2),
    EndDate         DATE
);

-- Sample Data for Customers
INSERT INTO Customers VALUES (1, 'Rajesh Kumar',    TO_DATE('1955-03-15', 'YYYY-MM-DD'), 15000.00, 'FALSE');
INSERT INTO Customers VALUES (2, 'Priya Sharma',    TO_DATE('1990-07-22', 'YYYY-MM-DD'), 12000.00, 'FALSE');
INSERT INTO Customers VALUES (3, 'Mohan Lal',       TO_DATE('1960-01-10', 'YYYY-MM-DD'),  8000.00, 'FALSE');
INSERT INTO Customers VALUES (4, 'Anita Desai',     TO_DATE('1958-11-05', 'YYYY-MM-DD'),  5000.00, 'FALSE');
INSERT INTO Customers VALUES (5, 'Vikram Singh',    TO_DATE('1985-06-30', 'YYYY-MM-DD'), 25000.00, 'FALSE');
INSERT INTO Customers VALUES (6, 'Sunita Verma',    TO_DATE('1962-09-18', 'YYYY-MM-DD'),  9500.00, 'FALSE');

-- Sample Data for Loans
INSERT INTO Loans VALUES (101, 1, 500000, 8.5,  TO_DATE('2026-07-15', 'YYYY-MM-DD'));
INSERT INTO Loans VALUES (102, 2, 300000, 9.0,  TO_DATE('2026-07-25', 'YYYY-MM-DD'));
INSERT INTO Loans VALUES (103, 3, 200000, 7.5,  TO_DATE('2026-08-10', 'YYYY-MM-DD'));
INSERT INTO Loans VALUES (104, 4, 150000, 10.0, TO_DATE('2026-07-05', 'YYYY-MM-DD'));
INSERT INTO Loans VALUES (105, 5, 800000, 8.0,  TO_DATE('2027-01-01', 'YYYY-MM-DD'));
INSERT INTO Loans VALUES (106, 6, 100000, 9.5,  TO_DATE('2026-07-20', 'YYYY-MM-DD'));

COMMIT;


-- ============================================================
-- Scenario 1: Apply 1% Discount on Loan Interest Rate
--             for Customers Aged Above 60
-- ============================================================

DECLARE
    v_age NUMBER;

    CURSOR c_customers IS
        SELECT c.CustomerID, c.Name, c.DOB, l.LoanID, l.InterestRate
        FROM Customers c
        JOIN Loans l ON c.CustomerID = l.CustomerID;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Scenario 1: Senior Citizen Loan Discount');
    DBMS_OUTPUT.PUT_LINE('========================================');

    FOR rec IN c_customers LOOP
        -- Calculate age using MONTHS_BETWEEN
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, rec.DOB) / 12);

        IF v_age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE LoanID = rec.LoanID;

            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' | Age: ' || v_age ||
                ' | Loan ID: ' || rec.LoanID ||
                ' | Old Rate: ' || rec.InterestRate || '%' ||
                ' | New Rate: ' || (rec.InterestRate - 1) || '%'
            );
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Discount applied successfully.');
END;
/


-- ============================================================
-- Scenario 2: Set IsVIP = 'TRUE' for Customers
--             with Balance Over $10,000
-- ============================================================

DECLARE
    CURSOR c_customers IS
        SELECT CustomerID, Name, Balance
        FROM Customers;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Scenario 2: VIP Status Assignment');
    DBMS_OUTPUT.PUT_LINE('========================================');

    FOR rec IN c_customers LOOP
        IF rec.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' | Balance: $' || rec.Balance ||
                ' | Status: Promoted to VIP'
            );
        ELSE
            DBMS_OUTPUT.PUT_LINE(
                'Customer: ' || rec.Name ||
                ' | Balance: $' || rec.Balance ||
                ' | Status: Not eligible for VIP'
            );
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('VIP status updated successfully.');
END;
/


-- ============================================================
-- Scenario 3: Send Reminders for Loans Due
--             Within the Next 30 Days
-- ============================================================

DECLARE
    CURSOR c_due_loans IS
        SELECT c.CustomerID, c.Name, l.LoanID, l.LoanAmount, l.EndDate
        FROM Customers c
        JOIN Loans l ON c.CustomerID = l.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30;

    v_days_remaining NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Scenario 3: Loan Due Reminders');
    DBMS_OUTPUT.PUT_LINE('========================================');

    FOR rec IN c_due_loans LOOP
        v_days_remaining := TRUNC(rec.EndDate - SYSDATE);

        DBMS_OUTPUT.PUT_LINE(
            'REMINDER: Dear ' || rec.Name ||
            ', your Loan (ID: ' || rec.LoanID ||
            ') of amount $' || rec.LoanAmount ||
            ' is due on ' || TO_CHAR(rec.EndDate, 'DD-MON-YYYY') ||
            '. Only ' || v_days_remaining || ' day(s) remaining!'
        );
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('All reminders sent successfully.');
END;
/
