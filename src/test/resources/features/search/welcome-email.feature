Feature: Welcome email 1 is delivered
  Welcome email 1 is delivered from CDH for new policy holders

    # Valid environments are "dev", "staging", "boe", "fpai"
  Background:
    Given Environment is "staging"

  Scenario: Welcome email 1 is delivered for policy generated  last 2  days
    Given Policy is generated for "new" customer within last "2" days
    When Files are uploaded to SFTP
    When Customers and policies are found in CDH
    And Customer and policy files are removed from SFTP
    And Campaign is run
    Then Email status is "Delivered" in CDH for "CPLNewPolicyholderWelcomeEmail1"

  Scenario: Welcome email 1 is not delivered for policy  generated in future
    Given Policy is generated for "new" customer after next "1" days
    When Files are uploaded to SFTP
    When Customers and policies are found in CDH
    And Customer and policy files are removed from SFTP
    And Campaign is run
    Then Email status is not delivered in CDH for "CPLNewPolicyholderWelcomeEmail1"