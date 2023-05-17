db = db.getSiblingDB('game')

db.createUser(
    {
        user: 'dev_user',
        pwd: 'NdEep0XLpMNKUmgQVa81oDCx7mrSRodh0Z79qdX3',
        roles: [{ role: 'readWrite', db: 'game' }],
    },
);
db.createCollection('user')
db.createCollection('history')
db.createCollection('statistics')