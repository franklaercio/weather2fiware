SELECT timestamp AT TIME ZONE 'UTC' AT TIME ZONE 'America/Recife' AS time, value
FROM (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY timestamp ORDER BY updatedat DESC) AS rn
    FROM mttemperature.ettemperature
    WHERE city = 'NATAL'
    ) sub
WHERE rn = 1
ORDER BY timestamp ASC;

SELECT
    time_index AT TIME ZONE 'UTC' AT TIME ZONE 'America/Recife' AS time,
    value
FROM mttemperature.ettemperature
WHERE city = 'NATAL'
  AND time_index >= (NOW() AT TIME ZONE 'America/Recife') AT TIME ZONE 'UTC'
  AND time_index < ((NOW() AT TIME ZONE 'America/Recife') + interval '1 hour') AT TIME ZONE 'UTC'
ORDER BY updatedat ASC
    LIMIT 1;

SELECT timestamp AT TIME ZONE 'UTC' AT TIME ZONE 'America/Recife' AS time, value
FROM (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY timestamp ORDER BY updatedat DESC) AS rn
    FROM mtprecipitation.etprecipitation
    WHERE city = 'NATAL'
    ) sub
WHERE rn = 1
ORDER BY timestamp ASC;

SELECT timestamp AT TIME ZONE 'UTC' AT TIME ZONE 'America/Recife' AS time, value
FROM (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY timestamp ORDER BY updatedat DESC) AS rn
    FROM mtrelativehumidity.etrelativehumidity
    WHERE city = 'NATAL'
    ) sub
WHERE rn = 1
ORDER BY timestamp ASC;