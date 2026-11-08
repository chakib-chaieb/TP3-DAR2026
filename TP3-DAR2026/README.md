# TP3-DAR2026 : Serveur multi-threads - sockets TCP

Faculté des Sciences de Sfax - Département d'informatique et des communications
Matière : Développement d'applications réparties - LSI 3 - AU 2026-2027
Réalisé par : Chaieb Chakib

## Structure du projet

```
TP3-DAR2026
├── src
│   ├── activite31      (serveur multi-threads, communication texte, compatible Telnet)
│   │   ├── Serveur.java
│   │   ├── ClientProcess.java
│   │   └── Client.java
│   └── activite32      (service calculatrice, communication par objets, compteur global synchronisé)
│       ├── Serveur.java
│       ├── ClientProcess.java
│       ├── Client.java
│       ├── Operation.java
│       └── Compteur.java
└── captures
```

## Activité 3-1 : acceptation de plusieurs clients

- `Serveur` écoute sur le port 1234 et crée un thread `ClientProcess` pour chaque connexion acceptée.
- Le serveur affiche l'adresse IP (`getRemoteSocketAddress()`) et le numéro d'ordre de chaque client.
- Chaque client reçoit son numéro d'ordre à la connexion.

### Exécution

1. Lancer `activite31.Serveur`.
2. Lancer plusieurs instances de `activite31.Client` (IntelliJ : Run > Edit Configurations > Allow multiple instances) ou des clients Telnet : `telnet localhost 1234`.
3. Test en réseau : `java activite31.Client <IP_du_serveur> 1234` depuis une autre machine.

### Captures d'écran

| Test | Capture |
|---|---|
| Serveur + 2 clients Telnet | ![telnet](captures/31_telnet.png) |
| Serveur + 2 clients Java | ![java](captures/31_clients_java.png) |
| Test réseau local | ![reseau](captures/31_reseau.png) |

### Synthèse

À compléter : le serveur accepte plusieurs clients simultanément grâce à un thread par client, chaque client reçoit son numéro d'ordre et le serveur affiche l'IP de chacun.

## Activité 3-2 : amélioration de l'application calculatrice

- La communication se fait par objets (`ObjectInputStream` / `ObjectOutputStream`) : le client envoie un objet `Operation` (sérialisable), le serveur le calcule et le renvoie avec le résultat ou une erreur.
- Le serveur écoute sur le port 1235.
- `Compteur` contient un compteur global partagé dont l'incrémentation est protégée par `synchronized` ; à chaque opération, le serveur affiche la nouvelle valeur.
- Opérations supportées : `+`, `-`, `*`, `/` (division par zéro gérée).

### Exécution

1. Lancer `activite32.Serveur`.
2. Lancer plusieurs `activite32.Client` et saisir des opérations au format `5 + 3`, `exit` pour quitter.
3. Test en réseau : `java activite32.Client <IP_du_serveur> 1235`.

### Captures d'écran

| Test | Capture |
|---|---|
| Plusieurs clients en local | ![local](captures/32_local.png) |
| Compteur global côté serveur | ![compteur](captures/32_compteur.png) |
| Test réseau local | ![reseau](captures/32_reseau.png) |

### Synthèse

À compléter : un thread par client permet un traitement en parallèle, et la synchronisation du compteur garantit qu'il reste cohérent même avec plusieurs clients simultanés.

## Remarques

- Le pare-feu de la machine serveur doit autoriser les ports 1234 et 1235.
- L'adresse IP du serveur s'obtient avec `ipconfig` (Windows) ou `ip a` (Linux).
