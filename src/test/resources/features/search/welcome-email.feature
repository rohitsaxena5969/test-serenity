Feature: Welcome email 1 is delivered
  Welcome email 1 is delivered from CDH for new policy holders

    # Valid environments are "dev", "staging", "boe", "fpai"
  Background:
    Given Environment is "staging"

  Scenario: Welcome email 1 is delivered for policy generated  last 2  days
    Given Policy is generated for customer within last "2" days
    When Files are uploaded to SFTP
    When Customers and policies are found in CDH
    And Customer and policy files are removed from SFTP
    And Campaign is run
    Then Email status is "Delivered" in CDH
