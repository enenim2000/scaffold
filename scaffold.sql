-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 08, 2019 at 02:24 PM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scaffold`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_providers`
--

CREATE TABLE `account_providers` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  `enabled` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `account_provider_types`
--

CREATE TABLE `account_provider_types` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `active_hours`
--

CREATE TABLE `active_hours` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `begin_time` time NOT NULL,
  `enabled` int(11) NOT NULL,
  `end_time` time NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `active_hours`
--

INSERT INTO `active_hours` (`id`, `created_at`, `updated_at`, `begin_time`, `enabled`, `end_time`, `name`) VALUES
(1, '2019-03-08 00:00:00', '2019-03-08 00:00:00', '12:07:17', 1, '22:07:17', 'Weekend');

-- --------------------------------------------------------

--
-- Table structure for table `audits`
--

CREATE TABLE `audits` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `_after` text,
  `_before` text,
  `crud_action` varchar(20) DEFAULT NULL,
  `dependency` text,
  `entity_type` varchar(100) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `task_route` varchar(80) DEFAULT NULL,
  `user_action` varchar(200) DEFAULT NULL,
  `user_agent` varchar(150) DEFAULT NULL,
  `authorization_id` bigint(20) DEFAULT NULL,
  `login_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `authorizations`
--

CREATE TABLE `authorizations` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `rid` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `branches`
--

CREATE TABLE `branches` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `address` varchar(200) NOT NULL,
  `enabled` int(11) DEFAULT NULL,
  `name` varchar(200) NOT NULL,
  `sol` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `branches`
--

INSERT INTO `branches` (`id`, `created_at`, `updated_at`, `address`, `enabled`, `name`, `sol`) VALUES
(1, '2019-03-08 00:00:00', '2019-03-08 00:00:00', '25 Adeyemo Alakija', 1, 'Head Office', '1005');

-- --------------------------------------------------------

--
-- Table structure for table `branch_public_holiday`
--

CREATE TABLE `branch_public_holiday` (
  `branch_id` bigint(20) NOT NULL,
  `public_holiday_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `_group` int(11) NOT NULL,
  `custom_url` varchar(100) DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `featured_rank` varchar(11) DEFAULT NULL,
  `icon` varchar(40) NOT NULL,
  `name` varchar(50) NOT NULL,
  `rank` int(11) NOT NULL,
  `slug` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `category_service`
--

CREATE TABLE `category_service` (
  `category_id` bigint(20) NOT NULL,
  `service_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `consumers`
--

CREATE TABLE `consumers` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `date_of_birth` varchar(10) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `enabled` int(11) DEFAULT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  `verified` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `consumer_accounts`
--

CREATE TABLE `consumer_accounts` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `account_name` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `branch_code` varchar(6) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `account_provider_id` bigint(20) DEFAULT NULL,
  `consumer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `consumer_settings`
--

CREATE TABLE `consumer_settings` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `_key` varchar(40) DEFAULT NULL,
  `_value` varchar(255) DEFAULT NULL,
  `consumer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `currencies`
--

CREATE TABLE `currencies` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  `code_digit` int(11) DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `html` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dispute_messages`
--

CREATE TABLE `dispute_messages` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `message` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `holiday_login` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `role` varchar(40) NOT NULL,
  `weekend_login` int(11) NOT NULL,
  `active_hour_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `created_at`, `updated_at`, `enabled`, `holiday_login`, `name`, `role`, `weekend_login`, `active_hour_id`) VALUES
(1, '2019-03-08 00:00:00', '2019-03-08 00:00:00', 1, 1, 'System Admin', 'System', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `amount_type` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `customer_id_label` varchar(255) NOT NULL,
  `description` varchar(100) NOT NULL,
  `enabled` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `pay_again` int(11) NOT NULL,
  `pay_again_type` int(11) DEFAULT NULL,
  `slug` varchar(200) NOT NULL,
  `surcharge` text,
  `biller_id` bigint(20) NOT NULL,
  `currency_id` bigint(20) NOT NULL,
  `sharing_formula_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `logins`
--

CREATE TABLE `logins` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_logged_in` date DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_type` varchar(50) NOT NULL,
  `username` varchar(70) NOT NULL,
  `verify_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logins`
--

INSERT INTO `logins` (`id`, `created_at`, `updated_at`, `last_logged_in`, `password`, `status`, `user_id`, `user_type`, `username`, `verify_status`) VALUES
(1, '2019-03-08 00:00:00', '2019-03-08 00:00:00', NULL, '$2a$10$jfnDMTc4OqdkcYk9DsGaGeANlrRa87C4JdTLbVnKm9XXtA4DMUEjm', 1, 1, 'Staff', 'System', 1);

-- --------------------------------------------------------

--
-- Table structure for table `log_events`
--

CREATE TABLE `log_events` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `details` text NOT NULL,
  `flagged_type` int(11) NOT NULL,
  `ip_address` varchar(50) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `login` varchar(80) NOT NULL,
  `request_data` text NOT NULL,
  `response_data` text NOT NULL,
  `status` int(11) NOT NULL,
  `task_name` varchar(30) NOT NULL,
  `task_route` varchar(80) NOT NULL,
  `type` int(11) NOT NULL,
  `uri` varchar(255) NOT NULL,
  `user_action` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `log_web_services`
--

CREATE TABLE `log_web_services` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `endpoint` varchar(255) NOT NULL,
  `http_method` varchar(10) NOT NULL,
  `login` varchar(80) NOT NULL,
  `narration` varchar(200) NOT NULL,
  `request` text NOT NULL,
  `response` text NOT NULL,
  `status_code` varchar(10) NOT NULL,
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `_key` varchar(255) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `model_id` varchar(255) NOT NULL,
  `model_type` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `login_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE `password_resets` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `login_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `payment_channels`
--

CREATE TABLE `payment_channels` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `enabled` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `notification_medium` varchar(20) NOT NULL,
  `txn_prefix` varchar(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment_channels`
--

INSERT INTO `payment_channels` (`id`, `created_at`, `updated_at`, `code`, `enabled`, `name`, `notification_medium`, `txn_prefix`) VALUES
(1, '2019-03-08 00:00:00', '2019-03-08 00:00:00', '002', 1, 'ADMIN APP', 'sms,email', 'adm');

-- --------------------------------------------------------

--
-- Table structure for table `payment_methods`
--

CREATE TABLE `payment_methods` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `code` varchar(4) NOT NULL,
  `enabled` int(11) NOT NULL,
  `holding_account` varchar(20) NOT NULL,
  `name` varchar(40) NOT NULL,
  `slug` varchar(40) NOT NULL,
  `txn_prefix` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `group_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `permission_authorizer`
--

CREATE TABLE `permission_authorizer` (
  `group_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `public_holidays`
--

CREATE TABLE `public_holidays` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `comment` text,
  `date` date NOT NULL,
  `holiday` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `secret_questions`
--

CREATE TABLE `secret_questions` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `answer` varchar(255) NOT NULL,
  `question` varchar(255) NOT NULL,
  `login_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `security_questions`
--

CREATE TABLE `security_questions` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `question` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `services`
--

CREATE TABLE `services` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `amount` double NOT NULL,
  `amount_type` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `discount` double NOT NULL,
  `enabled` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `slug` varchar(200) NOT NULL,
  `surcharge` double NOT NULL,
  `currency_id` bigint(20) NOT NULL,
  `vendor_id` bigint(20) NOT NULL,
  `category` varchar(255) NOT NULL,
  `payload` longtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `service_form`
--

CREATE TABLE `service_form` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `payload` varchar(255) DEFAULT NULL,
  `service_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `_category` varchar(50) NOT NULL,
  `_key` varchar(50) NOT NULL,
  `_value` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `employee_id` varchar(20) NOT NULL,
  `enabled` int(11) NOT NULL,
  `full_name` varchar(60) NOT NULL,
  `holiday_login` int(11) DEFAULT NULL,
  `weekend_login` int(11) DEFAULT NULL,
  `active_hour_id` bigint(20) DEFAULT NULL,
  `branch_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`, `created_at`, `updated_at`, `email`, `employee_id`, `enabled`, `full_name`, `holiday_login`, `weekend_login`, `active_hour_id`, `branch_id`, `group_id`) VALUES
(1, '2019-03-08 00:00:00', '2019-03-08 00:00:00', 'admin@gmail.com', '0000', 1, 'Asukwo Bassey', 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `extra` varchar(100) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `module` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `_order` int(11) DEFAULT NULL,
  `parent_task_id` bigint(20) DEFAULT NULL,
  `route` varchar(100) NOT NULL,
  `task_type` int(11) NOT NULL,
  `visibility` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `trackers`
--

CREATE TABLE `trackers` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `date_logged_in` datetime DEFAULT NULL,
  `date_logged_out` datetime DEFAULT NULL,
  `failed_attempts` int(11) NOT NULL,
  `ip_address` varchar(15) DEFAULT NULL,
  `logged_in` int(11) NOT NULL,
  `session_id` varchar(200) DEFAULT NULL,
  `time_of_last_activity` datetime DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `login_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trackers`
--

INSERT INTO `trackers` (`id`, `created_at`, `updated_at`, `date_logged_in`, `date_logged_out`, `failed_attempts`, `ip_address`, `logged_in`, `session_id`, `time_of_last_activity`, `user_agent`, `login_id`) VALUES
(1, '2019-03-08 13:21:21', '2019-03-08 13:21:21', '2019-03-08 13:21:21', NULL, 0, NULL, 1, '$2a$10$YBln7IEfj8lZUpZX0BgJf.4VqPJ0Wd9tzwCI6lDTo.ipi6pQFjU820.29655852024272533', '2019-03-08 13:21:21', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `amount` double NOT NULL,
  `amount_paid` double NOT NULL,
  `biller_discount` double NOT NULL,
  `customer_id` varchar(50) NOT NULL,
  `date_reversed` date DEFAULT NULL,
  `date_settled` date DEFAULT NULL,
  `date_paid` date DEFAULT NULL,
  `initiator` int(11) NOT NULL,
  `payment_channel_reference` varchar(100) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `reported_status_code` varchar(10) DEFAULT NULL,
  `reported_status_message` varchar(60) DEFAULT NULL,
  `sent_surcharge` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `surcharge` double NOT NULL,
  `transaction_reference` varchar(50) NOT NULL,
  `vat` double NOT NULL,
  `biller_id` bigint(20) NOT NULL,
  `branch_id` bigint(20) DEFAULT NULL,
  `consumer_id` bigint(20) DEFAULT NULL,
  `currency_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `payment_channel_id` bigint(20) NOT NULL,
  `payment_method_id` bigint(20) NOT NULL,
  `recurring_payment_id` bigint(20) DEFAULT NULL,
  `settlement_id` bigint(20) DEFAULT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `date_completed` date DEFAULT NULL,
  `vendor_discount` double NOT NULL,
  `service_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_disputes`
--

CREATE TABLE `transaction_disputes` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `message` text NOT NULL,
  `reference_number` varchar(255) NOT NULL,
  `remark` text,
  `status` int(11) NOT NULL,
  `transaction_reference` varchar(255) NOT NULL,
  `consumer_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_services`
--

CREATE TABLE `transaction_services` (
  `service_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_service_form`
--

CREATE TABLE `transaction_service_form` (
  `service_form_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vendors`
--

CREATE TABLE `vendors` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `enabled` int(11) NOT NULL,
  `logo_path` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `secret` varchar(100) DEFAULT NULL,
  `slug` varchar(50) DEFAULT NULL,
  `test_secret` varchar(100) DEFAULT NULL,
  `trading_name` varchar(100) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `verified` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vendor_accounts`
--

CREATE TABLE `vendor_accounts` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `account_name` varchar(100) NOT NULL,
  `account_number` varchar(20) NOT NULL,
  `enabled` int(11) NOT NULL,
  `account_provider_id` bigint(20) NOT NULL,
  `vendor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vendor_category`
--

CREATE TABLE `vendor_category` (
  `vendor_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vendor_payment_channel`
--

CREATE TABLE `vendor_payment_channel` (
  `vendor_id` bigint(20) NOT NULL,
  `payment_channel_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vendor_payment_method`
--

CREATE TABLE `vendor_payment_method` (
  `vendor_id` bigint(20) NOT NULL,
  `payment_method_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vendor_settings`
--

CREATE TABLE `vendor_settings` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `_key` varchar(40) NOT NULL,
  `_value` varchar(255) NOT NULL,
  `vendor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_providers`
--
ALTER TABLE `account_providers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_7wgrbdpjojrrctwnrqojxgjsp` (`code`),
  ADD UNIQUE KEY `UK_2eiqv93s4j72q13b68wcilq10` (`name`);

--
-- Indexes for table `account_provider_types`
--
ALTER TABLE `account_provider_types`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ctfppcyolmlg45vu21skyh00a` (`name`);

--
-- Indexes for table `active_hours`
--
ALTER TABLE `active_hours`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKc2d4pt0vrf636q8d3po0b8k1n` (`begin_time`,`end_time`),
  ADD UNIQUE KEY `UK_s7s8yxclp77tigjnsr45dp3iw` (`name`);

--
-- Indexes for table `audits`
--
ALTER TABLE `audits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlbfc4e2iq0g685l2sraef3cnc` (`authorization_id`),
  ADD KEY `FKs0cmdutux7jusjptgoas4hrcw` (`login_id`);

--
-- Indexes for table `authorizations`
--
ALTER TABLE `authorizations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_hrsi8oxqnl9id2dmtf1a9jv10` (`rid`),
  ADD KEY `FKjoaqd764ga8xtg4mr8q213pog` (`staff_id`),
  ADD KEY `FKninwuj2q1a4n0vcbo13yhww0x` (`task_id`);

--
-- Indexes for table `branches`
--
ALTER TABLE `branches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_66h5towl7qcfqgexvi3v9rc8a` (`sol`);

--
-- Indexes for table `branch_public_holiday`
--
ALTER TABLE `branch_public_holiday`
  ADD PRIMARY KEY (`branch_id`,`public_holiday_id`),
  ADD KEY `FK6ht2ej4tc5dt2qmgjecur25mb` (`public_holiday_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`),
  ADD UNIQUE KEY `UK_oul14ho7bctbefv8jywp5v3i2` (`slug`);

--
-- Indexes for table `category_service`
--
ALTER TABLE `category_service`
  ADD PRIMARY KEY (`category_id`,`service_id`),
  ADD KEY `FK1yj9xyyjp4p8vth4jhi6xbwgo` (`service_id`);

--
-- Indexes for table `consumers`
--
ALTER TABLE `consumers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ayhei5rt7ltp0aw86e5fl30tr` (`email`);

--
-- Indexes for table `consumer_accounts`
--
ALTER TABLE `consumer_accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKs6vwx3ntja7m9eq3fi73bpo94` (`account_provider_id`),
  ADD KEY `FK9nigyi01kakfg8s5qw4bra6m0` (`consumer_id`);

--
-- Indexes for table `consumer_settings`
--
ALTER TABLE `consumer_settings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeh2ys25o4hnotfqwkb36svuub` (`consumer_id`);

--
-- Indexes for table `currencies`
--
ALTER TABLE `currencies`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5r2dfxl1m7vus47ma0y05sflt` (`code`),
  ADD UNIQUE KEY `UK_fw6f3u7od723dsuspqlpjbkps` (`html`),
  ADD UNIQUE KEY `UK_a2yxotynwqjrmkq71won77vui` (`name`);

--
-- Indexes for table `dispute_messages`
--
ALTER TABLE `dispute_messages`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_42syqwky943luljc3kp2o8k87` (`message`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8mf0is8024pqmwjxgldfe54l7` (`name`),
  ADD UNIQUE KEY `UK_apk01ve5mbboqgaw14ag6kn1q` (`role`),
  ADD KEY `FKnm7nxls3l3grbt1ntrb46o29i` (`active_hour_id`);

--
-- Indexes for table `logins`
--
ALTER TABLE `logins`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_kpsgg434guljkbty611itckww` (`username`);

--
-- Indexes for table `log_events`
--
ALTER TABLE `log_events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `log_web_services`
--
ALTER TABLE `log_web_services`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK4bkdwef0mgpj3t0aursiqdi1o` (`login_id`,`_key`,`model_type`,`model_id`,`state`),
  ADD UNIQUE KEY `UK_p678k1khjd928rk3rusf072c` (`_key`),
  ADD UNIQUE KEY `UK_963m5syfphwrepev2wb0aoju` (`model_id`),
  ADD UNIQUE KEY `UK_9aulqtw4fou1d9wmx16n4ybtw` (`model_type`),
  ADD UNIQUE KEY `UK_ioynvopbbj0spnxruujbncyb2` (`state`);

--
-- Indexes for table `password_resets`
--
ALTER TABLE `password_resets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5jmdh277mpp0aw9rgplnco6q3` (`login_id`);

--
-- Indexes for table `payment_channels`
--
ALTER TABLE `payment_channels`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_34qr7vetxcw41l51haxfkf5ex` (`code`),
  ADD UNIQUE KEY `UK_6nouk7cwhapos8t1do45nm1vi` (`name`);

--
-- Indexes for table `payment_methods`
--
ALTER TABLE `payment_methods`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_m36ap4hf05m0uihe5ftw2omon` (`code`),
  ADD UNIQUE KEY `UK_kxvufx13wi6icen4i2wltdj02` (`name`),
  ADD UNIQUE KEY `UK_lcivbnfuu9o64tadp41la2vea` (`slug`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`group_id`,`task_id`),
  ADD KEY `FKcyj8f2dfv0eyf5kd6eejxxh1h` (`task_id`);

--
-- Indexes for table `permission_authorizer`
--
ALTER TABLE `permission_authorizer`
  ADD PRIMARY KEY (`group_id`,`task_id`),
  ADD KEY `FKpbjau6keh3cnaxn1gtesor44r` (`task_id`);

--
-- Indexes for table `public_holidays`
--
ALTER TABLE `public_holidays`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr9f18vdexle2mjrfuha5k76jy` (`date`,`holiday`),
  ADD UNIQUE KEY `UK_8h8aqgl4t5cfkegmj9umo6k3b` (`date`);

--
-- Indexes for table `secret_questions`
--
ALTER TABLE `secret_questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjd6tgxkawvr6shdh446lx7y2s` (`login_id`);

--
-- Indexes for table `security_questions`
--
ALTER TABLE `security_questions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sd69wse3norcwg3myxskh084c` (`question`);

--
-- Indexes for table `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6mrfnr8b2oilxwu2o2ufqesm7` (`vendor_id`,`code`),
  ADD UNIQUE KEY `UKmaayrxyj1vlk1lissbhdr8g4` (`vendor_id`,`name`),
  ADD UNIQUE KEY `UK_gnenm2itqjotnod9yfan1e9in` (`slug`),
  ADD UNIQUE KEY `UK8wvvcejiiuthp0nxtfqxsfsm7` (`vendor_id`,`category`),
  ADD KEY `FK70feh7q29o5d9hp115e09pbl1` (`currency_id`);

--
-- Indexes for table `service_form`
--
ALTER TABLE `service_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlhd4f3u8vh46msyqilphvcd3q` (`service_id`),
  ADD KEY `FKt43ufsk1lsstd3rpok7klolwi` (`transaction_id`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_1xosmfrlsfuunueyh2walyb7i` (`_key`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_pvctx4dbua9qh4p4s3gm3scrh` (`email`),
  ADD UNIQUE KEY `UK_gjxemtljj44lom4507vslmg5w` (`employee_id`),
  ADD KEY `FKdbctf2o1vjxftfj4496d6uqd0` (`active_hour_id`),
  ADD KEY `FK1lha0ag3td43wl4slo0mnujdq` (`branch_id`),
  ADD KEY `FKbn9n6mhul3clqn29narsm32bn` (`group_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_r47uph296nu6ov81x5dvj40nr` (`route`),
  ADD UNIQUE KEY `UKk1h5i212hho634g5rdtsu6rj9` (`parent_task_id`,`task_type`,`name`);

--
-- Indexes for table `trackers`
--
ALTER TABLE `trackers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_aeavs84e9llej6if348d9fwd2` (`session_id`),
  ADD KEY `FKl73yggb47tpye0v5rpsf580sv` (`login_id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_94m9euoxupnom1p8mtwmk9q4g` (`transaction_reference`),
  ADD KEY `FK96jfmjuu48u27s062xxlyolom` (`biller_id`),
  ADD KEY `FKti961feu1yn980b0kebj16cxh` (`branch_id`),
  ADD KEY `FK9x36vdpqvei3qh9137i9jss1i` (`consumer_id`),
  ADD KEY `FK812edr8o27pte306gvbmypytx` (`currency_id`),
  ADD KEY `FKq9uhpbfe5lk2mc79uwsk2ng83` (`item_id`),
  ADD KEY `FK5uc9xssm3xoumldokutcl6hc9` (`payment_channel_id`),
  ADD KEY `FK566q0xeai5k115ya39d776f3w` (`payment_method_id`),
  ADD KEY `FKgk6htwu73l9untiwiqwfkgn2d` (`recurring_payment_id`),
  ADD KEY `FKjhc6sot61chxhsutpbkwsuwl8` (`settlement_id`),
  ADD KEY `FKfner4cly0tqxmdut6udfxcb2b` (`staff_id`),
  ADD KEY `FKmiwtah1ipsay5mrb9alx2f8s1` (`service_id`);

--
-- Indexes for table `transaction_disputes`
--
ALTER TABLE `transaction_disputes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_pmv2i8s7lpm0rke6c9hya800b` (`reference_number`),
  ADD KEY `FK2m1a6n5al86wenml2pyfd85p6` (`consumer_id`),
  ADD KEY `FKe5lydafv6p752rpaugae2n1nq` (`transaction_id`);

--
-- Indexes for table `transaction_services`
--
ALTER TABLE `transaction_services`
  ADD PRIMARY KEY (`service_id`,`transaction_id`),
  ADD KEY `FKhle962cr64re2sk2oxlou7v8e` (`transaction_id`);

--
-- Indexes for table `transaction_service_form`
--
ALTER TABLE `transaction_service_form`
  ADD PRIMARY KEY (`service_form_id`,`transaction_id`),
  ADD KEY `FKiu85h6fc138414o01e4uv0bpa` (`transaction_id`);

--
-- Indexes for table `vendors`
--
ALTER TABLE `vendors`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8xmc0rpdougqaftbgr1s2tolo` (`email`),
  ADD UNIQUE KEY `UK_40r1c97ake8kj5ggsfd4dbtwn` (`code`),
  ADD UNIQUE KEY `UK_8gpiupg8e1lh7i37fi9gh2c88` (`secret`),
  ADD UNIQUE KEY `UK_326w4yy3sqwkmpnd4gaqqfb13` (`slug`),
  ADD UNIQUE KEY `UK_ii2vs9stwksiq9qu8tkj1cstx` (`test_secret`),
  ADD UNIQUE KEY `UK_67c7twe68r9pja5uskb68akgb` (`trading_name`);

--
-- Indexes for table `vendor_accounts`
--
ALTER TABLE `vendor_accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKf7114n4u1sk9bca5q9gfidevc` (`account_provider_id`,`account_number`),
  ADD KEY `FKb3s4sygkwv0yjir5994aecj7f` (`vendor_id`);

--
-- Indexes for table `vendor_category`
--
ALTER TABLE `vendor_category`
  ADD PRIMARY KEY (`vendor_id`,`category_id`),
  ADD KEY `FKis9yy089n9428a296kwbi4kyt` (`category_id`);

--
-- Indexes for table `vendor_payment_channel`
--
ALTER TABLE `vendor_payment_channel`
  ADD PRIMARY KEY (`vendor_id`,`payment_channel_id`),
  ADD KEY `FK4wgfl3crody9psvnd25dfngnq` (`payment_channel_id`);

--
-- Indexes for table `vendor_payment_method`
--
ALTER TABLE `vendor_payment_method`
  ADD PRIMARY KEY (`vendor_id`,`payment_method_id`),
  ADD KEY `FKkmuig2ch3463oe2ilnboff1pq` (`payment_method_id`);

--
-- Indexes for table `vendor_settings`
--
ALTER TABLE `vendor_settings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3jp5mdx6l7dvrdwu1n6s38ula` (`vendor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account_providers`
--
ALTER TABLE `account_providers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `account_provider_types`
--
ALTER TABLE `account_provider_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `active_hours`
--
ALTER TABLE `active_hours`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `audits`
--
ALTER TABLE `audits`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `authorizations`
--
ALTER TABLE `authorizations`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `branches`
--
ALTER TABLE `branches`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consumers`
--
ALTER TABLE `consumers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consumer_accounts`
--
ALTER TABLE `consumer_accounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consumer_settings`
--
ALTER TABLE `consumer_settings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `currencies`
--
ALTER TABLE `currencies`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `dispute_messages`
--
ALTER TABLE `dispute_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `logins`
--
ALTER TABLE `logins`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `log_events`
--
ALTER TABLE `log_events`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `log_web_services`
--
ALTER TABLE `log_web_services`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `password_resets`
--
ALTER TABLE `password_resets`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment_channels`
--
ALTER TABLE `payment_channels`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `payment_methods`
--
ALTER TABLE `payment_methods`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `public_holidays`
--
ALTER TABLE `public_holidays`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `secret_questions`
--
ALTER TABLE `secret_questions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `security_questions`
--
ALTER TABLE `security_questions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `services`
--
ALTER TABLE `services`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `service_form`
--
ALTER TABLE `service_form`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `trackers`
--
ALTER TABLE `trackers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `transaction_disputes`
--
ALTER TABLE `transaction_disputes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendors`
--
ALTER TABLE `vendors`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendor_accounts`
--
ALTER TABLE `vendor_accounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendor_settings`
--
ALTER TABLE `vendor_settings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `audits`
--
ALTER TABLE `audits`
  ADD CONSTRAINT `FKlbfc4e2iq0g685l2sraef3cnc` FOREIGN KEY (`authorization_id`) REFERENCES `authorizations` (`id`),
  ADD CONSTRAINT `FKs0cmdutux7jusjptgoas4hrcw` FOREIGN KEY (`login_id`) REFERENCES `logins` (`id`);

--
-- Constraints for table `authorizations`
--
ALTER TABLE `authorizations`
  ADD CONSTRAINT `FKjoaqd764ga8xtg4mr8q213pog` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  ADD CONSTRAINT `FKninwuj2q1a4n0vcbo13yhww0x` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`);

--
-- Constraints for table `branch_public_holiday`
--
ALTER TABLE `branch_public_holiday`
  ADD CONSTRAINT `FK6158t5ult9rvf13cy09fji49m` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`),
  ADD CONSTRAINT `FK6ht2ej4tc5dt2qmgjecur25mb` FOREIGN KEY (`public_holiday_id`) REFERENCES `public_holidays` (`id`);

--
-- Constraints for table `category_service`
--
ALTER TABLE `category_service`
  ADD CONSTRAINT `FK1yj9xyyjp4p8vth4jhi6xbwgo` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
  ADD CONSTRAINT `FK4hjsseb7ahh8nqd4dhtuhg52a` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `consumer_accounts`
--
ALTER TABLE `consumer_accounts`
  ADD CONSTRAINT `FK9nigyi01kakfg8s5qw4bra6m0` FOREIGN KEY (`consumer_id`) REFERENCES `consumers` (`id`),
  ADD CONSTRAINT `FKs6vwx3ntja7m9eq3fi73bpo94` FOREIGN KEY (`account_provider_id`) REFERENCES `account_providers` (`id`);

--
-- Constraints for table `consumer_settings`
--
ALTER TABLE `consumer_settings`
  ADD CONSTRAINT `FKeh2ys25o4hnotfqwkb36svuub` FOREIGN KEY (`consumer_id`) REFERENCES `consumers` (`id`);

--
-- Constraints for table `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `FKnm7nxls3l3grbt1ntrb46o29i` FOREIGN KEY (`active_hour_id`) REFERENCES `active_hours` (`id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `FK6eu9gdl81u8to633tdqjgrtxw` FOREIGN KEY (`login_id`) REFERENCES `logins` (`id`);

--
-- Constraints for table `password_resets`
--
ALTER TABLE `password_resets`
  ADD CONSTRAINT `FK5jmdh277mpp0aw9rgplnco6q3` FOREIGN KEY (`login_id`) REFERENCES `logins` (`id`);

--
-- Constraints for table `permission`
--
ALTER TABLE `permission`
  ADD CONSTRAINT `FK92c4t5irje6pbceyi8f5tmqp0` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `FKcyj8f2dfv0eyf5kd6eejxxh1h` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`);

--
-- Constraints for table `permission_authorizer`
--
ALTER TABLE `permission_authorizer`
  ADD CONSTRAINT `FKpbjau6keh3cnaxn1gtesor44r` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  ADD CONSTRAINT `FKpll8nqv9ba2xpgk11gut88v6r` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`);

--
-- Constraints for table `secret_questions`
--
ALTER TABLE `secret_questions`
  ADD CONSTRAINT `FKjd6tgxkawvr6shdh446lx7y2s` FOREIGN KEY (`login_id`) REFERENCES `logins` (`id`);

--
-- Constraints for table `services`
--
ALTER TABLE `services`
  ADD CONSTRAINT `FK70feh7q29o5d9hp115e09pbl1` FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`),
  ADD CONSTRAINT `FKdbq9pf3iawru3e2fisp6poaoq` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`);

--
-- Constraints for table `service_form`
--
ALTER TABLE `service_form`
  ADD CONSTRAINT `FKlhd4f3u8vh46msyqilphvcd3q` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
  ADD CONSTRAINT `FKt43ufsk1lsstd3rpok7klolwi` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `FK1lha0ag3td43wl4slo0mnujdq` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`),
  ADD CONSTRAINT `FKbn9n6mhul3clqn29narsm32bn` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `FKdbctf2o1vjxftfj4496d6uqd0` FOREIGN KEY (`active_hour_id`) REFERENCES `active_hours` (`id`);

--
-- Constraints for table `trackers`
--
ALTER TABLE `trackers`
  ADD CONSTRAINT `FKl73yggb47tpye0v5rpsf580sv` FOREIGN KEY (`login_id`) REFERENCES `logins` (`id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `FK566q0xeai5k115ya39d776f3w` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`id`),
  ADD CONSTRAINT `FK5uc9xssm3xoumldokutcl6hc9` FOREIGN KEY (`payment_channel_id`) REFERENCES `payment_channels` (`id`),
  ADD CONSTRAINT `FK812edr8o27pte306gvbmypytx` FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`),
  ADD CONSTRAINT `FK9x36vdpqvei3qh9137i9jss1i` FOREIGN KEY (`consumer_id`) REFERENCES `consumers` (`id`),
  ADD CONSTRAINT `FKfner4cly0tqxmdut6udfxcb2b` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  ADD CONSTRAINT `FKmiwtah1ipsay5mrb9alx2f8s1` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`),
  ADD CONSTRAINT `FKti961feu1yn980b0kebj16cxh` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);

--
-- Constraints for table `transaction_disputes`
--
ALTER TABLE `transaction_disputes`
  ADD CONSTRAINT `FK2m1a6n5al86wenml2pyfd85p6` FOREIGN KEY (`consumer_id`) REFERENCES `consumers` (`id`),
  ADD CONSTRAINT `FKe5lydafv6p752rpaugae2n1nq` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`);

--
-- Constraints for table `transaction_services`
--
ALTER TABLE `transaction_services`
  ADD CONSTRAINT `FKhle962cr64re2sk2oxlou7v8e` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`),
  ADD CONSTRAINT `FKifte9g6nxeypjn9dvisinaxbx` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`);

--
-- Constraints for table `transaction_service_form`
--
ALTER TABLE `transaction_service_form`
  ADD CONSTRAINT `FK81feiyd6oaeqfgjl846qd4wsm` FOREIGN KEY (`service_form_id`) REFERENCES `service_form` (`id`),
  ADD CONSTRAINT `FKiu85h6fc138414o01e4uv0bpa` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`id`);

--
-- Constraints for table `vendor_accounts`
--
ALTER TABLE `vendor_accounts`
  ADD CONSTRAINT `FKb3s4sygkwv0yjir5994aecj7f` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`),
  ADD CONSTRAINT `FKf24139pv03ve1vq4if7npqoeo` FOREIGN KEY (`account_provider_id`) REFERENCES `account_providers` (`id`);

--
-- Constraints for table `vendor_category`
--
ALTER TABLE `vendor_category`
  ADD CONSTRAINT `FKis9yy089n9428a296kwbi4kyt` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKso4cdsrq14noj1j6j6441hrnj` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`);

--
-- Constraints for table `vendor_payment_channel`
--
ALTER TABLE `vendor_payment_channel`
  ADD CONSTRAINT `FK4wgfl3crody9psvnd25dfngnq` FOREIGN KEY (`payment_channel_id`) REFERENCES `payment_channels` (`id`),
  ADD CONSTRAINT `FK6qlpp2eyg6r9tlt68ok968hp2` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`);

--
-- Constraints for table `vendor_payment_method`
--
ALTER TABLE `vendor_payment_method`
  ADD CONSTRAINT `FK12l8p1epdx538kakewp6hsdrm` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`),
  ADD CONSTRAINT `FKkmuig2ch3463oe2ilnboff1pq` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`id`);

--
-- Constraints for table `vendor_settings`
--
ALTER TABLE `vendor_settings`
  ADD CONSTRAINT `FK3jp5mdx6l7dvrdwu1n6s38ula` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
