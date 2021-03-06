-- вероятность что следующий день будет в том же направлении, что и предыдущие два дня (показатель трендовости)

SELECT
  count(1),
  sum(ind) / count(1)

FROM
  (
    SELECT
      CLOSE_DATETIME,
      CASE WHEN (LAG_1 = LAG_2)
        THEN 1
      ELSE 0 END AS ind
    FROM
      (
        SELECT
          CLOSE_DATETIME,
          previous_price_higher,
          LAG(previous_price_higher, 1)
          OVER (
            ORDER BY CLOSE_DATETIME) AS LAG_1,
          LAG(previous_price_higher, 2)
          OVER (
            ORDER BY CLOSE_DATETIME) AS LAG_2
        FROM
          (
            SELECT
              CLOSE_DATETIME,
              CASE WHEN (CLOSE_PRICE - previous_price_1) >= 0
                THEN -1
              ELSE 1 END AS previous_price_higher
            FROM
              (
                SELECT
                  CLOSE_PRICE,
                  CLOSE_DATETIME,
                  LAG(CLOSE_PRICE) -- предыдущая строка
                  OVER (
                    ORDER BY CLOSE_DATETIME
                  ) previous_price_1
                FROM SBRF_SPLICE__D1
                ORDER BY CLOSE_DATETIME
              )
          )
      )
  );

-- каркас иерархического запроса с выборкой по месяцам
WITH
    first_month (n, date_from, date_until) AS (
      SELECT
        1                          AS n,
        to_char(DATE '2014-01-01') AS date_from,
        to_char(DATE '2014-01-31') AS date_until
      FROM DUAL
  )
  , counts ( n, date_from, date_until, probability ) AS (
  SELECT
    n,
    to_char(date_from)                                                  AS date_from,
    to_char(date_until)                                                 AS date_until,
    (SELECT count(1)
     FROM SBRF_SPLICE__D1
     WHERE date_from <= CLOSE_DATETIME AND CLOSE_DATETIME < date_until) AS probability
  FROM first_month
  UNION ALL
  SELECT
    n + 1                              AS n,
    to_char(add_months(date_from, 1))  AS date_from,
    to_char(add_months(date_until, 1)) AS date_until,
    (
      SELECT count(1)
      FROM SBRF_SPLICE__D1
      WHERE 1 = 1
            AND add_months(date_from, 1) <= CLOSE_DATETIME
            AND CLOSE_DATETIME < add_months(date_until, 1)
    )                                  AS probability
  FROM counts
  WHERE n < 50
) SELECT *
  FROM counts;

-- вероятность того что после дня роста (close > open) будет день роста
-- вероятность того что после дня падения будет день падения
WITH first_row(n, date_from, date_until) AS (
    SELECT
      1                          AS n,
      to_char(DATE '2014-01-01') AS date_from,
      to_char(DATE '2014-01-31') AS date_until
    FROM DUAL
), next_row (n, date_from, date_until, probability) AS (
    SELECT
      n,
      to_char(date_from)  AS date_from,
      to_char(date_until) AS date_until,
      (
        SELECT
          --direction,
          (sum(ind2) / count(1)) AS probability
        FROM (
          SELECT
            CLOSE_PRICE,
            CLOSE_DATETIME,
            (
              CASE WHEN ind = 1
                THEN 'UP'
              ELSE 'DOWN' END
            ) AS direction,
            ind,
            previour_ind,
            (
              CASE WHEN ind = previour_ind
                THEN 1
              ELSE 0 END
            ) AS ind2
          FROM (
            SELECT
              CLOSE_PRICE,
              CLOSE_DATETIME,
              (
                CASE WHEN (CLOSE_PRICE - OPEN_PRICE) >= 0
                  THEN 1
                ELSE -1 END
              )                          AS ind,
              lag(
                  CASE WHEN (CLOSE_PRICE - OPEN_PRICE) >= 0
                    THEN 1
                  ELSE -1 END
              )
              OVER (
                ORDER BY CLOSE_DATETIME) AS previour_ind

            FROM SBRF_SPLICE__D1 q
            WHERE to_date(date_from) <= CLOSE_DATETIME
                  AND CLOSE_DATETIME < to_date(date_until)
            ORDER BY CLOSE_DATETIME
          )
        )
        GROUP BY direction
      )                   AS probability
    FROM first_row

)
SELECT *
FROM next_row;


SELECT
  direction,
  (sum(ind2) / count(1)) AS probability

FROM (
  SELECT
    CLOSE_PRICE,
    CLOSE_DATETIME,
    (
      CASE WHEN ind = 1
        THEN 'UP'
      ELSE 'DOWN' END
    ) AS direction,
    ind,
    previour_ind,
    (
      CASE WHEN ind = previour_ind
        THEN 1
      ELSE 0 END
    ) AS ind2

  FROM (
    SELECT
      CLOSE_PRICE,
      CLOSE_DATETIME,
      (
        CASE WHEN (CLOSE_PRICE - OPEN_PRICE) >= 0
          THEN 1
        ELSE -1 END
      )                          AS ind,
      lag(
          CASE WHEN (CLOSE_PRICE - OPEN_PRICE) >= 0
            THEN 1
          ELSE -1 END
      )
      OVER (
        ORDER BY CLOSE_DATETIME) AS previour_ind

    FROM SBRF_SPLICE__D1 q
    ORDER BY CLOSE_DATETIME
  )
)
GROUP BY direction;