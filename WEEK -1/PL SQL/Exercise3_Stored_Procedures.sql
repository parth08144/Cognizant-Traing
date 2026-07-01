-- ============================================================
-- Exercise 3: Stored Procedures
-- ============================================================


-- ============================================================
-- Table Setup (Run these first to create the required tables)
-- ============================================================

-- Accounts Table
CREATE TABLE Accounts (
    AccountID       NUMBER PRIMARY KEY,
    CustomerID      NUMBER,
    AccountType     VARCHAR2(20),    -- 'Savings' or 'Current'
    Balance         NUMBER(15, 2)
);

-- Employees Table
CREATE TABLE Employees (
    EmployeeID      NUMBER PRIMARY KEY,
    Name            VARCHAR2(100),
    Department      VARCHAR2(50),
    Salary          NUMBER(15, 2)
);

-- Sample Data for Accounts
INSERT INTO Accounts VALUES (1001, 1, 'Savings', 50000.00);
INSERT INTO Accounts VALUES (1002, 2, 'Current', 75000.00);
INSERT INTO Accounts VALUES (1003, 3, 'Savings', 30000.00);
INSERT INTO Accounts VALUES (1004, 4, 'Savings', 120000.00);
INSERT INTO Accounts VALUES (1005, 5, 'Current', 45000.00);
INSERT INTO Accounts VALUES (1006, 6, 'Savings', 90000.00);

-- Sample Data for Employees
INSERT INTO Employees VALUES (201, 'Amit Patel',     'IT',      60000.00);
INSERT INTO Employees VALUES (202, 'Neha Gupta',     'HR',      55000.00);
INSERT INTO Employees VALUES (203, 'Ravi Shankar',   'IT',      70000.00);
INSERT INTO Employees VALUES (204, 'Pooja Mehta',    'Finance', 65000.00);
INSERT INTO Employees VALUES (205, 'Karan Joshi',    'HR',      50000.00);
INSERT INTO Employees VALUES (206, 'Deepa Nair',     'Finance', 72000.00);

COMMIT;


-- ============================================================
-- Scenario 1: Process Monthly Interest for Savings Accounts
--   Applies 1% interest to all savings account balances
-- ============================================================

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
IS
    v_interest NUMBER(15, 2);

    CURSOR c_savings IS
        SELECT AccountID, Balance
        FROM Accounts
        WHERE AccountType = 'Savings';
BEGIN
    DBMS_OUTPUT.PUT_LINE('==========================================');
    DBMS_OUTPUT.PUT_LINE('Processing Monthly Interest (1%) ...');
    DBMS_OUTPUT.PUT_LINE('==========================================');

    FOR rec IN c_savings LOOP
        v_interest := rec.Balance * 0.01;

        UPDATE Accounts
        SET Balance = Balance + v_interest
        WHERE AccountID = rec.AccountID;

        DBMS_OUTPUT.PUT_LINE(
            'Account ID: ' || rec.AccountID ||
            ' | Old Balance: ' || rec.Balance ||
            ' | Interest: ' || v_interest ||
            ' | New Balance: ' || (rec.Balance + v_interest)
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest applied successfully.');
END;
/




-- ============================================================
-- Scenario 2: Update Employee Bonus Based on Department
--   Adds a bonus percentage to salary for a given department
-- ============================================================

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department   IN VARCHAR2,
    p_bonus_pct    IN NUMBER
)
IS
    v_bonus   NUMBER(15, 2);
    v_count   NUMBER := 0;

    CURSOR c_employees IS
        SELECT EmployeeID, Name, Salary
        FROM Employees
        WHERE Department = p_department;
BEGIN
    DBMS_OUTPUT.PUT_LINE('==========================================');
    DBMS_OUTPUT.PUT_LINE('Updating Bonus for Department: ' || p_department);
    DBMS_OUTPUT.PUT_LINE('Bonus Percentage: ' || p_bonus_pct || '%');
    DBMS_OUTPUT.PUT_LINE('==========================================');

    FOR rec IN c_employees LOOP
        v_bonus := rec.Salary * (p_bonus_pct / 100);

        UPDATE Employees
        SET Salary = Salary + v_bonus
        WHERE EmployeeID = rec.EmployeeID;

        DBMS_OUTPUT.PUT_LINE(
            'Employee: ' || rec.Name ||
            ' | Old Salary: ' || rec.Salary ||
            ' | Bonus: ' || v_bonus ||
            ' | New Salary: ' || (rec.Salary + v_bonus)
        );

        v_count := v_count + 1;
    END LOOP;

    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE(v_count || ' employee(s) updated successfully.');
    END IF;
END;
/




-- ============================================================
-- Scenario 3: Transfer Funds Between Accounts
--   Transfers amount from source to destination account
--   with sufficient balance check
-- ============================================================

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_source_acc   IN NUMBER,
    p_dest_acc     IN NUMBER,
    p_amount       IN NUMBER
)
IS
    v_source_balance  NUMBER(15, 2);
    v_source_exists   NUMBER;
    v_dest_exists     NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('==========================================');
    DBMS_OUTPUT.PUT_LINE('Fund Transfer Request');
    DBMS_OUTPUT.PUT_LINE('From Account: ' || p_source_acc ||
                         ' | To Account: ' || p_dest_acc ||
                         ' | Amount: ' || p_amount);
    DBMS_OUTPUT.PUT_LINE('==========================================');

    -- Validate transfer amount
    IF p_amount <= 0 THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Transfer amount must be greater than zero.');
        RETURN;
    END IF;

    -- Check if source account exists
    SELECT COUNT(*) INTO v_source_exists
    FROM Accounts
    WHERE AccountID = p_source_acc;

    IF v_source_exists = 0 THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Source account ' || p_source_acc || ' does not exist.');
        RETURN;
    END IF;

    -- Check if destination account exists
    SELECT COUNT(*) INTO v_dest_exists
    FROM Accounts
    WHERE AccountID = p_dest_acc;

    IF v_dest_exists = 0 THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Destination account ' || p_dest_acc || ' does not exist.');
        RETURN;
    END IF;

    -- Check sufficient balance in source account
    SELECT Balance INTO v_source_balance
    FROM Accounts
    WHERE AccountID = p_source_acc;

    IF v_source_balance < p_amount THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Insufficient balance in source account.');
        DBMS_OUTPUT.PUT_LINE('Available Balance: ' || v_source_balance ||
                             ' | Requested: ' || p_amount);
        RETURN;
    END IF;

    -- Debit from source account
    UPDATE Accounts
    SET Balance = Balance - p_amount
    WHERE AccountID = p_source_acc;

    -- Credit to destination account
    UPDATE Accounts
    SET Balance = Balance + p_amount
    WHERE AccountID = p_dest_acc;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Transfer of ' || p_amount || ' completed successfully.');
    DBMS_OUTPUT.PUT_LINE('Source Account ' || p_source_acc ||
                         ' New Balance: ' || (v_source_balance - p_amount));
END;
/


